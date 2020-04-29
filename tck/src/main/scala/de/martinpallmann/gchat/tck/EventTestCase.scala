package de.martinpallmann.gchat.tck

import de.martinpallmann.gchat.Event

abstract private[tck] class EventTestCase {
  def event: Event
  override def toString: String = event.toString
}
