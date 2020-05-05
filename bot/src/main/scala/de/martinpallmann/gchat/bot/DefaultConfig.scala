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

package de.martinpallmann.gchat.bot

import de.martinpallmann.gchat.bot.config.GoogleChatApiConfig
import io.circe.Json
import io.circe.parser.parse
import org.slf4j.LoggerFactory
import cats.implicits._

import scala.io.Codec.UTF8
import scala.io.Source

class DefaultConfig extends Config {

  private val logger = LoggerFactory.getLogger(getClass)

  def env: Map[String, String] = sys.env

  def banner: List[String] =
    Source.fromResource("banner.txt")(UTF8).getLines.toList

  def ipAddress: String = "0.0.0.0"

  def authEnabled: Boolean =
    env.contains("GOOGLE_CHAT_API_CONFIG")

  def port: Int =
    (for {
      p0 <- env.get("PORT")
      p1 <- p0.toIntOption
    } yield p1).getOrElse(9000)

  def api: Option[GoogleChatApiConfig] =
    for {
      s <- env.get("GOOGLE_CHAT_API_CONFIG")
      j <- parseJson(s)
      c <- GoogleChatApiConfig.fromJson(j)
    } yield c

  private def parseJson(s: String): Option[Json] =
    parse(s).fold(
      f => {
        logger.debug(f.show)
        None
      },
      Some(_)
    )
}

object DefaultConfig {
  def apply(): Config = new DefaultConfig()
}
