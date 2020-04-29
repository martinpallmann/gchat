package de.martinpallmann.gchat.event

sealed trait UserMentionUser
object UserMentionUser {
  case class Bot(avatarUrl: String, displayName: String, name: String)
      extends UserMentionUser
}
