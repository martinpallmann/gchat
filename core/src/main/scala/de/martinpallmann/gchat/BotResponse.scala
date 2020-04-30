package de.martinpallmann.gchat

import de.martinpallmann.gchat.gen.Message

object BotResponse {

  def empty: Message =
    Message()

  def text(s: String): Message =
    Message(text = Some(s))
}
