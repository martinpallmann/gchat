package de.martinpallmann.gchat.event

sealed trait Annotation

object Annotation {
  case class UserMentionAnnotation(length: Int,
                                   startIndex: Int,
                                   userMention: UserMention)
      extends Annotation

}
