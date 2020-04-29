package de.martinpallmann.gchat.circe.event

import de.martinpallmann.gchat.event.{Annotation, UserMention}
import io.circe.{Decoder, DecodingFailure, HCursor}
import io.circe.generic.semiauto._
import AnnotationDecoder._
import de.martinpallmann.gchat.event.Annotation.UserMentionAnnotation

trait AnnotationDecoder extends UserMentionDecoder {

  implicit val decodeUserMentionAnnotation: Decoder[UserMentionAnnotation] =
    deriveDecoder[UserMentionAnnotation]

  implicit val decodeAnnotation: Decoder[Annotation] = (c: HCursor) =>
    for {
      t <- c.downField("type").as[AnnotationType]
      r <- t match {
        case AnnotationType.UserMention => decodeUserMentionAnnotation(c)
      }
    } yield r
}

object AnnotationDecoder {
  sealed trait AnnotationType
  object AnnotationType {
    case object UserMention extends AnnotationType
    implicit val annotationTypeDecoder: Decoder[AnnotationType] =
      Decoder.decodeString.emap {
        case "USER_MENTION" => Right(UserMention)
        case x              => Left(s"unkown type: $x")
      }
  }
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
