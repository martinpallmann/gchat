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

package de.martinpallmann.gchat.bot.auth

import java.io.ByteArrayInputStream
import java.net.URL
import java.security.cert.CertificateFactory
import java.security.interfaces.RSAPublicKey

import cats.effect.IO
import io.circe.{Json, JsonObject}
import org.slf4j.LoggerFactory

import scala.io.Source
import io.circe.parser.parse

import scala.util.Try

class PublicKeyStorage(data: Map[String, RSAPublicKey]) {

  def get(keyId: String): Option[RSAPublicKey] =
    data.get(keyId)

}

object PublicKeyStorage {
  private val logger = LoggerFactory.getLogger(getClass)

  def apply(url: String): IO[PublicKeyStorage] =
    for {
      keys <- publicKeys(url)
    } yield new PublicKeyStorage(keys)

  private def publicKeys(url: String): IO[Map[String, RSAPublicKey]] = {

    def extractPublicKey(certValue: String): Try[RSAPublicKey] =
      Try {
        CertificateFactory
          .getInstance("X.509")
          .generateCertificate(
            new ByteArrayInputStream(certValue.getBytes("UTF-8"))
          )
          .getPublicKey
          .asInstanceOf[RSAPublicKey]
      }

    def theKeys: PartialFunction[(String, Json), (String, RSAPublicKey)] = {
      kv: (String, Json) =>
        kv match {
          case (k, v) =>
            v.asString
              .flatMap(s => extractPublicKey(s).toOption)
              .map(publicKey => k -> publicKey)
        }
    }.unlift

    def read(url: String): IO[String] =
      IO.fromTry(Try {
        Source
          .fromInputStream(new URL(url).openStream(), "UTF-8") // uaaaah
          .getLines()
          .mkString("\n")
      })

    for {
      source <- read(url)
      parsed <- IO.fromEither(parse(source))
      jsonOb <- IO.fromEither(parsed.as[JsonObject])
    } yield jsonOb.toMap.view
      .collect(theKeys)
      .toMap
  }
}
