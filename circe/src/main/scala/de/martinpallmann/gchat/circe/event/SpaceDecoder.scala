package de.martinpallmann.gchat.circe.event

import de.martinpallmann.gchat.event.Space
import io.circe.{Decoder, HCursor}
import de.martinpallmann.gchat.event.Space.{Dm, Room}
import io.circe.generic.semiauto._
import SpaceDecoder._

trait SpaceDecoder {

  implicit val decodeDm: Decoder[Dm] = deriveDecoder[Dm]
  implicit val decodeRoom: Decoder[Room] = deriveDecoder[Room]

  implicit val decodeSpace: Decoder[Space] = (c: HCursor) =>
    for {
      t <- c.downField("type").as[SpaceType]
      r <- t match {
        case SpaceType.Dm   => decodeDm(c)
        case SpaceType.Room => decodeRoom(c)
      }
    } yield r
}

object SpaceDecoder {
  sealed trait SpaceType
  object SpaceType {
    case object Dm extends SpaceType
    case object Room extends SpaceType
    implicit val eventTypeDecoder: Decoder[SpaceType] =
      Decoder.decodeString.emap {
        case "DM"   => Right(Dm)
        case "ROOM" => Right(Room)
        case x      => Left(s"unkown type: $x")
      }
  }
}
