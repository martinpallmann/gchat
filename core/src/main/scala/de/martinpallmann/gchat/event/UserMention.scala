package de.martinpallmann.gchat.event

sealed trait UserMention
object UserMention {
  case class Mention(user: UserMentionUser) extends UserMention
}
