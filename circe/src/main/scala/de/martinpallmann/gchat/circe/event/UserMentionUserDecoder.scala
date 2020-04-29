package de.martinpallmann.gchat.circe.event

import de.martinpallmann.gchat.circe.event.UserMentionUserDecoder.UserMentionUserType
import de.martinpallmann.gchat.event.UserMentionUser
import io.circe.generic.semiauto.deriveDecoder
import io.circe.{Decoder, HCursor}

trait UserMentionUserDecoder {

  implicit val decodeBot: Decoder[UserMentionUser.Bot] =
    deriveDecoder[UserMentionUser.Bot]

  implicit val decodeUserMentionUser: Decoder[UserMentionUser] = (c: HCursor) =>
    for {
      t <- c.downField("type").as[UserMentionUserType]
      r <- t match {
        case UserMentionUserType.Bot => decodeBot(c)
      }
    } yield r
}

object UserMentionUserDecoder {
  sealed trait UserMentionUserType
  object UserMentionUserType {
    case object Bot extends UserMentionUserType
    implicit val userMentionTypeDecoder: Decoder[UserMentionUserType] =
      Decoder.decodeString.emap {
        case "BOT" => Right(Bot)
        case x     => Left(s"unkown type: $x")
      }
  }
}
