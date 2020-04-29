package de.martinpallmann.gchat

import java.time.Instant

sealed trait Event {
  val eventTime: Instant
}

object Event {
  case class AddedToSpace(eventTime: Instant,
                          space: event.Space,
                          message: Option[event.Message],
                          user: event.User)
      extends Event

  case class RemovedFromSpace(eventTime: Instant,
                              space: event.Space,
                              user: event.User)
      extends Event

  case class Message(eventTime: Instant,
                     space: event.Space,
                     message: event.Message,
                     user: event.User)
      extends Event
}
