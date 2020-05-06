package de.martinpallmann.gchat.tck

import de.martinpallmann.gchat.gen.Message

abstract private[tck] class BotResponseTestCase {
  def response: Message
  override def toString: String = response.toString
}
