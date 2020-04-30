import ch.qos.logback.classic.{Level, LoggerContext, PatternLayout}
import ch.qos.logback.classic.spi.{Configurator, ILoggingEvent}
import ch.qos.logback.core.encoder.LayoutWrappingEncoder
import ch.qos.logback.core.{ConsoleAppender, Context}
import ch.qos.logback.core.spi.ContextAwareBase
import org.slf4j.Logger

class LoggingConfig extends ContextAwareBase with Configurator {
  def configure(lc: LoggerContext): Unit = {

    val ca = appender(lc)

    val encoder = new LayoutWrappingEncoder[ILoggingEvent]
    encoder.setContext(lc)

    val layout = pLayout(lc)

    encoder.setLayout(layout)

    ca.setEncoder(encoder)
    ca.start()
    lc.getLogger(Logger.ROOT_LOGGER_NAME).setLevel(Level.INFO)
    lc.getLogger(Logger.ROOT_LOGGER_NAME).addAppender(ca)

  }

  def appender(lc: LoggerContext): ConsoleAppender[ILoggingEvent] = {
    val ca = new ConsoleAppender[ILoggingEvent]
    ca.setContext(lc)
    ca.setName("console")
    ca;
  }

  def pLayout(lc: LoggerContext): PatternLayout = {
    val layout = new PatternLayout()
    layout.setPattern(
      "%highlight(%-5level) %cyan(%logger)\n      %replace(%msg){'(\n)','$1      '}%n"
    );
    layout.setContext(lc)
    layout.start()
    layout
  }
}
