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

package de.martinpallmann.gchat.tck.request

import java.time.Instant

import de.martinpallmann.gchat.BotRequest
import de.martinpallmann.gchat.gen._
import de.martinpallmann.gchat.tck.BotRequestTestCase

class MessageTestCase extends BotRequestTestCase {
  def request: BotRequest =
    BotRequest.MessageReceived(
      Instant.parse("2017-03-02T19:02:59.910959Z"),
      Space(
        name = "spaces/AAAAAAAAAAA",
        displayName = "Chuck Norris Discussion Room",
        `type` = SpaceType.Room
      ),
      Message(
        name = "spaces/AAAAAAAAAAA/messages/CCCCCCCCCCC",
        sender = User(
          name = "users/12345678901234567890",
          displayName = "Chuck Norris"
        ),
        createTime = Instant.parse("2017-03-02T19:02:59.910959Z"),
        text = "@TestBot Violence is my last option.",
        argumentText = " Violence is my last option.",
        thread = Thread(name = "spaces/AAAAAAAAAAA/threads/BBBBBBBBBBB"),
        annotations = List(
          Annotation(
            length = 8,
            startIndex = 0,
            userMention = UserMentionMetadata(
              `type` = UserMentionMetadataType.Mention,
              user = User(
                displayName = "TestBot",
                name = "users/1234567890987654321",
                `type` = UserType.Bot
              )
            ),
            `type` = AnnotationType.UserMention
          )
        )
      ),
      User(name = "users/12345678901234567890", displayName = "Chuck Norris")
    )
}
