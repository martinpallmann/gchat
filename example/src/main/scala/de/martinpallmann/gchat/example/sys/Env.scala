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

package de.martinpallmann.gchat.example.sys

import org.slf4j.LoggerFactory

object Env {

  private val logger = LoggerFactory.getLogger(getClass)

  def withLog[A](o: Option[A])(log: => String): Option[A] =
    o match {
      case s @ Some(_) => s
      case n @ None    => logger.debug(log); n
    }

  def withDefault[A](key: String, fallback: A)(f: String => Option[A]): A =
    (for {
      v <- sys.env.get(key)
      a <- withLog(f(v))(s"could not convert string to '${fallback.getClass}'")
    } yield a).getOrElse(fallback)

  def apply(key: String): Option[String] =
    withLog(sys.env.get(key))(s"could not retrieve env var: '$key'")
}
