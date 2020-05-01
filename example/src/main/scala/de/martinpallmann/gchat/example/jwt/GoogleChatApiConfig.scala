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

package de.martinpallmann.gchat.example.jwt

import de.martinpallmann.gchat.example.sys.Env
import io.circe._
import io.circe.parser
import org.slf4j.LoggerFactory
import cats.implicits._

case class GoogleChatApiConfig(
  `type`: String,
  projectId: String,
  privateKeyId: String,
  privateKey: String,
  clientEmail: String,
  clientId: String,
  authUri: String,
  tokenUri: String,
  authProviderX509CertUrl: String,
  clientX509CertUrl: String)

object GoogleChatApiConfig {

  private val logger = LoggerFactory.getLogger(getClass)

  implicit val googleChatApiConfigDecoder: Decoder[GoogleChatApiConfig] =
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

  def parse(s: String): Option[Json] =
    parser
      .parse(s)
      .fold[Option[Json]](
        f => {
          logger.debug(s"could not parse json. ${f.show}")
          None
        },
        Some(_)
      )

  def loadFromEnv: Option[GoogleChatApiConfig] =
    for {
      cfgEnv <- Env("GOOGLE_CHAT_API_CONFIG")
      json <- parse(cfgEnv)
      cfg <- load(json)
    } yield cfg

  def load(json: Json): Option[GoogleChatApiConfig] =
    json
      .as[GoogleChatApiConfig]
      .fold[Option[GoogleChatApiConfig]](
        t => {
          logger.debug(s"could not decode json ${t.show}")
          None
        },
        c => Some(c)
      )
}
