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

package de.martinpallmann.gchat.example.logging

import ch.qos.logback.classic.{LoggerContext, PatternLayout}
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.{LayoutBase, Layout => Lay}

object Layout {

  val pattern: String =
    "%highlight(%-5level) %cyan(%logger)\n      %replace(%msg){'(\n)','$1      '}%n"

  def apply()(implicit lc: LoggerContext): Lay[ILoggingEvent] = {
    val layout = sys.env.get("LOGGING") match {
      case Some("LIVE") => kvLayout
      case _            => patternLayout(pattern)
    }
    layout.setContext(lc)
    layout.start()
    layout
  }

  def patternLayout(pattern: String): Lay[ILoggingEvent] = {
    val result = new PatternLayout()
    result.setPattern(pattern)
    result
  }

  def kvLayout: Lay[ILoggingEvent] =
    new LayoutBase[ILoggingEvent] {

      private def shouldQuote(s: String): Boolean =
        s.contains(" ")

      private def quoteValue(s: String): String =
        if (shouldQuote(s)) s""""$s""""
        else s

      private def escape(s: String): String =
        s.replace("\"", "'").replace("\n", "\\n")

      private def put(kv: (String, String)*): String = {
        kv.map {
            case (k, v) => s"""$k=${quoteValue(escape(v))}"""
          }
          .mkString("", " ", "\n")
      }

      def doLayout(event: ILoggingEvent): String =
        put(
          "at" -> s"${event.getLevel}".toLowerCase,
          "message" -> event.getMessage,
          "logger" -> event.getLoggerName
        )
    }
}
