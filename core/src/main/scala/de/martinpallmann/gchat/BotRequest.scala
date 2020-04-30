package de.martinpallmann.gchat

import gen._
import java.time.Instant

sealed trait BotRequest {
  val eventTime: Instant
}

object BotRequest {
  case class AddedToSpace(
    eventTime: Instant,
    space: Space,
    message: Option[Message],
    user: User)
      extends BotRequest

  case class RemovedFromSpace(
    eventTime: Instant,
    space: Space,
    user: User)
      extends BotRequest

  case class MessageReceived(
    eventTime: Instant,
    space: Space,
    message: Message,
    user: User)
      extends BotRequest
}
