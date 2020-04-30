package de.martinpallmann.gchat.example.logging

import ch.qos.logback.classic.Level.INFO
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.spi.{Configurator, ILoggingEvent}
import ch.qos.logback.core.{Appender, Layout => LogbackLayout}
import ch.qos.logback.core.encoder.{Encoder, LayoutWrappingEncoder}
import ch.qos.logback.core.spi.ContextAwareBase
import org.slf4j.Logger.ROOT_LOGGER_NAME

class Config extends ContextAwareBase with Configurator {

  def configure(lc: LoggerContext): Unit = {
    implicit val loggingContext: LoggerContext = lc
    implicit val ca: Appender[ILoggingEvent] = ConsoleAppender(
      encoder(Layout()),
    )
    Logger(ROOT_LOGGER_NAME, INFO)
  }

  private def encoder(
    layout: LogbackLayout[ILoggingEvent],
  )(implicit lc: LoggerContext,
  ): Encoder[ILoggingEvent] = {
    val result = new LayoutWrappingEncoder[ILoggingEvent]
    result.setContext(lc)
    result.setLayout(layout)
    result
  }
}
