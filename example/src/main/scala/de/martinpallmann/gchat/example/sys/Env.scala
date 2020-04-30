package de.martinpallmann.gchat.example.sys

import org.slf4j.LoggerFactory

object Env {

  private val logger = LoggerFactory.getLogger(getClass)

  def apply(key: String): Option[String] =
    sys.env.get(key) match {
      case s @ Some(_) => s
      case n @ None    => logger.debug(s"could not retrieve env var: '$key'"); n
    }
}
