package de.martinpallmann.gchat.tck.event

import java.time.Instant

import de.martinpallmann.gchat.{Event, Message, Space, SpaceType, Thread, User}
import de.martinpallmann.gchat.Event.MessageEvent
import de.martinpallmann.gchat.tck.EventTestCase

class MessageTestCase extends EventTestCase {
  def event: Event = {
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
