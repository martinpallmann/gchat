package de.martinpallmann.gchat.example.logging

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.Layout
import ch.qos.logback.core.encoder.{LayoutWrappingEncoder, Encoder => Enc}

object Encoder {
  def apply(
    layout: Layout[ILoggingEvent],
  )(implicit lc: LoggerContext,
  ): Enc[ILoggingEvent] = {
    val result = new LayoutWrappingEncoder[ILoggingEvent]
    result.setContext(lc)
    result.setLayout(layout)
    result
  }
}
