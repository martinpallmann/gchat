package de.martinpallmann.gchat.example.logging

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.{Appender, ConsoleAppender => ConsoleApp}
import ch.qos.logback.core.encoder.Encoder

object ConsoleAppender {
  def apply(
    encoder: Encoder[ILoggingEvent],
  )(implicit lc: LoggerContext,
  ): Appender[ILoggingEvent] = {
    val ca = new ConsoleApp[ILoggingEvent]
    ca.setContext(lc)
    ca.setName("console")
    ca.setEncoder(encoder)
    ca.start()
    ca
  }
}
