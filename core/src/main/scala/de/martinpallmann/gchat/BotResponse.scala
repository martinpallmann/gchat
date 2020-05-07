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

import scala.language.implicitConversions

object BotResponse {

  implicit def toOption[A](a: A): Option[A] = Some(a)

  implicit def toTextParagraph(s: String): Option[gen.TextParagraph] =
    Some(gen.TextParagraph(Some(s)))

  def empty: gen.Message =
    gen.Message()

  def text(s: String): gen.Message =
    gen.Message(text = s)

  def card(title: String, subtitle: String): gen.Message =
    gen.Message(cards =
      List(
        gen.Card(
          header = gen.CardHeader(title = title, subtitle = subtitle),
          sections = List(
            gen.Section(
              header = "section header",
              widgets = List(gen.WidgetMarkup(textParagraph = "text paragraph"))
            )
          )
        )
      )
    )

  def textCard(text: String): gen.Message =
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

  def imageCard(
    title: String,
    subtitle: String,
    imageUrl: String
  ): gen.Message =
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
