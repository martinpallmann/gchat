package de.martinpallmann.gchat.circe.event

import io.circe.{Decoder, HCursor}
import UserMentionDecoder._
import de.martinpallmann.gchat.event.UserMention
import io.circe.generic.semiauto._

trait UserMentionDecoder extends UserMentionUserDecoder {

  implicit val decodeMention: Decoder[UserMention.Mention] =
    deriveDecoder[UserMention.Mention]

  implicit val decodeUserMention: Decoder[UserMention] = (c: HCursor) =>
    for {
      t <- c.downField("type").as[UserMentionType]
      r <- t match {
        case UserMentionType.Mention => decodeMention(c)
      }
    } yield r
}

object UserMentionDecoder {
  sealed trait UserMentionType
  object UserMentionType {
    case object Mention extends UserMentionType
    implicit val userMentionTypeDecoder: Decoder[UserMentionType] =
      Decoder.decodeString.emap {
        case "MENTION" => Right(Mention)
        case x         => Left(s"unkown type: $x")
      }
  }
}
