package de.martinpallmann.gchat.example.logging

import ch.qos.logback.classic.{Level, LoggerContext}
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.Appender

object Logger {
  def apply(
    name: String,
    level: Level
  )(implicit lc: LoggerContext, appender: Appender[ILoggingEvent]): Unit = {
    lc.getLogger(name).setLevel(level)
    lc.getLogger(name).addAppender(appender)
  }
}
