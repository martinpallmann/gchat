package de.martinpallmann.gchat.tck

import de.martinpallmann.gchat.BotRequest

abstract private[tck] class BotRequestTestCase {
  def request: BotRequest
  override def toString: String = request.toString
}
