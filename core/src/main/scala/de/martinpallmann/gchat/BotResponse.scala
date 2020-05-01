/*
 * Copyright 2020 Martin Pallmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.martinpallmann.gchat

import de.martinpallmann.gchat.gen.Message

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

  case class ImageCard(
    title: String,
    subtitle: String,
    imageUrl: String)
      extends BotResponse {
    def toMessage: gen.Message =
      gen.Message(cards =
        List(
          gen.Card(
            header = gen.CardHeader(title = title, subtitle = subtitle),
            sections = List(
              gen.Section(widgets =
                List(gen.WidgetMarkup(image = gen.Image(imageUrl = imageUrl)))
              )
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
