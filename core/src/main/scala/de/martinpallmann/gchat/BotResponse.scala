package de.martinpallmann.gchat

import scala.language.implicitConversions

sealed trait BotResponse {
  def toMessage: gen.Message
}

object BotResponse {

  implicit def toOption[A](a: A): Option[A] = Some(a)

  implicit def toTextParagraph(s: String): Option[gen.TextParagraph] =
    Some(gen.TextParagraph(Some(s)))

  case class Card(title: String, subtitle: String) extends BotResponse {
    def toMessage: gen.Message =
      gen.Message(cards =
        List(
          gen.Card(
            header = gen.CardHeader(title = title, subtitle = subtitle),
            sections = List(
              gen.Section(
                header = "section header",
                widgets =
                  List(gen.WidgetMarkup(textParagraph = "text paragraph"))
              )
            )
          )
        )
      )
  }

  case class TextCard(text: String) extends BotResponse {
    def toMessage: gen.Message =
      gen.Message(cards =
        List(
          gen.Card(sections =
            List(
              gen
                .Section(widgets = List(gen.WidgetMarkup(textParagraph = text)))
            )
          )
        )
      )
  }

  case class Text(text: String) extends BotResponse {
    def toMessage: gen.Message =
      gen.Message(text = text)
  }

  case object Empty extends BotResponse {
    def toMessage: gen.Message =
      gen.Message()
  }
}
