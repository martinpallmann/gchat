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
          "message" -> event.getMessage
        )
    }
}
