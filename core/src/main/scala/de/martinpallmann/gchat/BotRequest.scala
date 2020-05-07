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

import gen._
import java.time.Instant

sealed trait BotRequest {
  val eventTime: Instant
}

object BotRequest {
  case class AddedToSpace(
    eventTime: Instant,
    space: Space,
    message: Option[Message],
    user: User)
      extends BotRequest

  case class RemovedFromSpace(
    eventTime: Instant,
    space: Space,
    user: User)
      extends BotRequest

  case class MessageReceived(
    eventTime: Instant,
    space: Space,
    message: Message,
    user: User)
      extends BotRequest

  case class CardClicked(
    eventTime: Instant,
    space: Space,
    message: Message,
    user: User,
    action: FormAction)
      extends BotRequest
}
