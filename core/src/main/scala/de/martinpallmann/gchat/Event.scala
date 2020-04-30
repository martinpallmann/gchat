package de.martinpallmann.gchat

import gen._
import java.time.Instant

sealed trait Event {
  val eventTime: Instant
}

object Event {
  case class AddedToSpaceEvent(
    eventTime: Instant,
    space: Space,
    message: Option[Message],
    user: User)
      extends Event

  case class RemovedFromSpaceEvent(
    eventTime: Instant,
    space: Space,
    user: User)
      extends Event

  case class MessageEvent(
    eventTime: Instant,
    space: Space,
    message: Message,
    user: User)
      extends Event
}
