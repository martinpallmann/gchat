package de.martinpallmann.gchat.tck

import de.martinpallmann.gchat.BotRequest

abstract private[tck] class EventTestCase {
  def event: BotRequest
  override def toString: String = event.toString
}
