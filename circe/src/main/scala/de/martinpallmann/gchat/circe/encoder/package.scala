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

package de.martinpallmann.gchat.circe

import java.time.Instant

import de.martinpallmann.gchat.gen._
import io.circe.Encoder
import io.circe.Encoder.encodeString
import io.circe.generic.semiauto._

package object encoder {
  implicit val encodeInstant: Encoder[Instant] =
    encodeString.contramap[Instant](_.toString)

  implicit val encodeTextParagraph: Encoder[TextParagraph] =
    deriveEncoder[TextParagraph].mapJson(_.dropNullValues)

  implicit val encodeOpenLink: Encoder[OpenLink] =
    deriveEncoder[OpenLink].mapJson(_.dropNullValues)

  implicit val encodeActionParameter: Encoder[ActionParameter] =
    deriveEncoder[ActionParameter].mapJson(_.dropNullValues)

  implicit val encodeFormAction: Encoder[FormAction] =
    deriveEncoder[FormAction].mapJson(_.dropNullValues)

  implicit val encodeOnClick: Encoder[OnClick] =
    deriveEncoder[OnClick].mapJson(_.dropNullValues)

  implicit val encodeKeyValueIcon: Encoder[KeyValueIcon] =
    enumEncoder[KeyValueIcon]

  implicit val encodeKeyValue: Encoder[KeyValue] =
    deriveEncoder[KeyValue].mapJson(_.dropNullValues)

  implicit val encodeImage: Encoder[Image] =
    deriveEncoder[Image].mapJson(_.dropNullValues)

  implicit val encodeTextButton: Encoder[TextButton] =
    deriveEncoder[TextButton].mapJson(_.dropNullValues)

  implicit val encodeImageButtonIcon: Encoder[ImageButtonIcon] =
    enumEncoder[ImageButtonIcon]

  implicit val encodeImageButton: Encoder[ImageButton] =
    deriveEncoder[ImageButton].mapJson(_.dropNullValues)

  implicit val encodeButton: Encoder[Button] =
    deriveEncoder[Button].mapJson(_.dropNullValues)

  implicit val encoderWidgetMarkup: Encoder[WidgetMarkup] =
    deriveEncoder[WidgetMarkup].mapJson(_.dropNullValues)

  implicit val encodeSection: Encoder[Section] =
    deriveEncoder[Section].mapJson(_.dropNullValues)

  implicit val encodeCardAction: Encoder[CardAction] =
    deriveEncoder[CardAction].mapJson(_.dropNullValues)

  implicit val encodeCardHeaderImageStyle: Encoder[CardHeaderImageStyle] =
    enumEncoder[CardHeaderImageStyle]

  implicit val encodeCardHeader: Encoder[CardHeader] =
    deriveEncoder[CardHeader].mapJson(_.dropNullValues)

  implicit val encodeCard: Encoder[Card] =
    deriveEncoder[Card].mapJson(_.dropNullValues)

  implicit val encodeActionResponseType: Encoder[ActionResponseType] =
    enumEncoder[ActionResponseType]

  implicit val encodeActionResponse: Encoder[ActionResponse] =
    deriveEncoder[ActionResponse].mapJson(_.dropNullValues)

  implicit val encodeUser: Encoder[User] =
    deriveEncoder[User].mapJson(_.dropNullValues)

  implicit val encodeUserMentionMetadataType: Encoder[UserMentionMetadataType] =
    enumEncoder[UserMentionMetadataType]

  implicit val encodeUserMentionMetadata: Encoder[UserMentionMetadata] =
    deriveEncoder[UserMentionMetadata].mapJson(_.dropNullValues)

  implicit val encodeAnnotationType: Encoder[AnnotationType] =
    enumEncoder[AnnotationType]

  implicit val encodeAnnotation: Encoder[Annotation] =
    deriveEncoder[Annotation].mapJson(_.dropNullValues)

  implicit val encodeSpaceType: Encoder[SpaceType] =
    enumEncoder[SpaceType]

  implicit val encodeSpace: Encoder[Space] =
    deriveEncoder[Space].mapJson(_.dropNullValues)

  implicit val encodeThread: Encoder[Thread] =
    deriveEncoder[Thread].mapJson(_.dropNullValues)

  implicit val encodeUserType: Encoder[UserType] =
    enumEncoder[UserType]

  implicit val encodeMessage: Encoder[Message] =
    deriveEncoder[Message].mapJson(_.deepDropNullValues)

  private def enumEncoder[A]: Encoder[A] = {
    val snakeCase: String => String = _.foldLeft("") {
      case ("", c)             => s"${c.toUpper}"
      case (s, c) if c.isUpper => s"${s}_$c"
      case (s, c)              => s"${s}${c.toUpper}"
    }
    encodeString.contramap[A](x => snakeCase(x.toString))
  }
}
