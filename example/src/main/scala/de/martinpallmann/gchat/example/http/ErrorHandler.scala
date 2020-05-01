/*
 * Copyright 2020 Martin Pallmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.martinpallmann.gchat.example.http

import cats.Monad
import io.circe.DecodingFailure
import org.http4s.headers.{`Content-Length`, Connection}
import org.http4s.util.CaseInsensitiveString
import org.http4s._
import org.slf4j.{Logger, LoggerFactory}
import cats.implicits._

import scala.util.control.NonFatal

object ErrorHandler {

  val logger: Logger = LoggerFactory.getLogger(getClass)

  def decodingFailure(mf: MessageFailure): Boolean =
    mf.cause.exists(_.isInstanceOf[DecodingFailure])

  def fmt(curl: String): String =
    curl
      .foldLeft(("", false)) {
        case ((s, false), c) if c == '-' => (s"$s\\\n  -", false)
        case ((s, x), c) if c == '\''    => (s"$s$c", !x)
        case ((s, x), c)                 => (s"$s$c", x)
      }
      ._1

  def curl[F[_]](req: Request[F]): String = {
    def escapeQuotationMarks(s: String) = s.replaceAll("'", """'\\''""")
    def host =
      req.headers
        .get(CaseInsensitiveString("host"))
        .map(_.value)
        .getOrElse("") + escapeQuotationMarks(req.uri.renderString)

    val elements = (
      host ::
        s"-X ${req.method.name}" ::
        req.headers
          .filterNot(_.name.equals(CaseInsensitiveString("host")))
          .redactSensitive(Headers.SensitiveHeaders.contains)
          .toList
          .map { header =>
            s"-H '${escapeQuotationMarks(header.toString)}'"
          }
    ).filter(_.nonEmpty).mkString(" \\\n  ")
    s"curl $elements"
  }

  def apply[F[_]](
    implicit F: Monad[F]
  ): Request[F] => PartialFunction[Throwable, F[Response[F]]] =
    req => {

      case mf: MessageFailure if decodingFailure(mf) =>
        val cause = mf.getCause().asInstanceOf[DecodingFailure].show
        logger.warn(s"$cause\n${curl(req)}")
        mf.toHttpResponse[F](req.httpVersion).pure[F]

      case mf: MessageFailure =>
        mf.toHttpResponse[F](req.httpVersion).pure[F]

      case NonFatal(t) =>
        logger.warn(s"error in request. ${t}")
        F.pure(
          Response(
            Status.InternalServerError,
            req.httpVersion,
            Headers(
              Connection(CaseInsensitiveString("close")) ::
                `Content-Length`.zero ::
                Nil
            )
          )
        )
    }
}
