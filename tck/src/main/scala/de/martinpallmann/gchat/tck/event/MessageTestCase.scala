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

package de.martinpallmann.gchat.tck.event

import java.time.Instant

import de.martinpallmann.gchat.BotRequest
import de.martinpallmann.gchat.gen._
import de.martinpallmann.gchat.tck.EventTestCase

class MessageTestCase extends EventTestCase {
  def event: BotRequest = {
    ???
//    MessageEvent(
//      Instant.parse("2017-03-02T19:02:59.910959Z"),
//      Space("spaces/AAAAAAAAAAA", "Chuck Norris Discussion Room", SpaceType.Room),
//      Message(
//        Instant.parse("2017-03-02T19:02:59.910959Z"),
//        "spaces/AAAAAAAAAAA/messages/CCCCCCCCCCC",
//        User(
//          "https://lh3.googleusercontent.com/.../photo.jpg",
//          "Chuck Norris",
//          "users/12345678901234567890",
//          "chuck@example.com"
//        ),
//
//        "@TestBot Violence is my last option.",
//        Thread("spaces/AAAAAAAAAAA/threads/BBBBBBBBBBB"),
//        Some(
//          List(
//            UserMentionAnnotation(
//              8,
//              0,
//              UserMention.Mention(
//                UserMentionUser.Bot(
//                  "https://.../avatar.png",
//                  "TestBot",
//                  "users/1234567890987654321"
//                )
//              )
//            )
//          )
//        ),
//        " Violence is my last option."
//      ),
//      User(
//        "https://lh3.googleusercontent.com/.../photo.jpg",
//        "Chuck Norris",
//        "users/12345678901234567890",
//        "chuck@example.com"
//      )
//    )
  }
}
