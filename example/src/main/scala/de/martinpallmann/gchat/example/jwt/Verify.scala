package de.martinpallmann.gchat.example.jwt

import org.slf4j.LoggerFactory

object Verify {
  private val logger = LoggerFactory.getLogger(getClass)
  def verify(): Unit = {
    logger.info(GoogleChatApiConfig.loadFromEnv.map(_.`type`).toString)
  }
}
