package de.martinpallmann.gchat

import de.martinpallmann.gchat.gen.Message

sealed trait BotResponse {
  def toMessage: Message
}

object BotResponse {

  case class Text(text: String) extends BotResponse {
    def toMessage: Message = Message(text = Some(text))
  }

  case object Empty extends BotResponse {
    def toMessage: Message = Message()
  }
}
