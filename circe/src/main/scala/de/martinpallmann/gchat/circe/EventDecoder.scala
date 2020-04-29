package de.martinpallmann.gchat.circe

import de.martinpallmann.gchat.Event
import de.martinpallmann.gchat.circe.event.{
  InstantDecoder,
  MessageDecoder,
  SpaceDecoder,
  UserDecoder
}
import io.circe.{Decoder, HCursor}
import io.circe.generic.semiauto._
import EventDecoder._

trait EventDecoder
    extends InstantDecoder
    with SpaceDecoder
    with MessageDecoder
    with UserDecoder {

  implicit val decodeEventAdded: Decoder[Event.AddedToSpace] =
    deriveDecoder[Event.AddedToSpace]

  implicit val decodeEventMessage: Decoder[Event.Message] =
    deriveDecoder[Event.Message]

  implicit val decodeEventRemoved: Decoder[Event.RemovedFromSpace] =
    deriveDecoder[Event.RemovedFromSpace]

  implicit val decodeEvent: Decoder[Event] = (c: HCursor) =>
    for {
      t <- c.downField("type").as[EventType]
      r <- t match {
        case EventType.AddedToSpace     => decodeEventAdded(c)
        case EventType.Message          => decodeEventMessage(c)
        case EventType.RemovedFromSpace => decodeEventRemoved(c)
      }
    } yield r
}

object EventDecoder {
  private sealed trait EventType
  private object EventType {

    case object AddedToSpace extends EventType
    case object Message extends EventType
    case object RemovedFromSpace extends EventType
    implicit val eventTypeDecoder: Decoder[EventType] =
      Decoder.decodeString.emap {
        case "ADDED_TO_SPACE"     => Right(AddedToSpace)
        case "MESSAGE"            => Right(Message)
        case "REMOVED_FROM_SPACE" => Right(RemovedFromSpace)
        case x                    => Left(s"unkown type: $x")
      }
  }
}
