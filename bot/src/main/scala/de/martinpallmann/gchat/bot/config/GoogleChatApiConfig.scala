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

package de.martinpallmann.gchat.bot.config

import java.net.URI

import io.circe.{Decoder, DecodingFailure, Json}
import cats.implicits._
import org.slf4j.LoggerFactory

import scala.util.Try

case class GoogleChatApiConfig(
  `type`: String,
  projectId: String,
  privateKeyId: String,
  privateKey: String,
  clientEmail: String,
  clientId: String,
  authUri: URI,
  tokenUri: URI,
  authProviderX509CertUrl: URI,
  clientX509CertUrl: URI)

object GoogleChatApiConfig {

  private implicit val decodeUri: Decoder[URI] =
    Decoder.decodeString.emapTry(s => Try { new URI(s) })

  def fromJson(json: Json): Option[GoogleChatApiConfig] =
    json
      .as[GoogleChatApiConfig](
        Decoder.forProduct10(
          "type",
          "project_id",
          "private_key_id",
          "private_key",
          "client_email",
          "client_id",
          "auth_uri",
          "token_uri",
          "auth_provider_x509_cert_url",
          "client_x509_cert_url"
        )(GoogleChatApiConfig.apply)
      )
      .fold(logFailure, Some(_))

  private val logger = LoggerFactory.getLogger(getClass)

  private def logFailure[A](f: DecodingFailure): Option[A] = {
    logger.debug(f.show)
    None
  }
}
