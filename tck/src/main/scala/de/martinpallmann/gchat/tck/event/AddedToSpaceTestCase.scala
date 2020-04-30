package de.martinpallmann.gchat.tck.event

import java.time.Instant
import de.martinpallmann.gchat.Event
import de.martinpallmann.gchat.gen._
import de.martinpallmann.gchat.Event.AddedToSpaceEvent
import de.martinpallmann.gchat.tck.EventTestCase

class AddedToSpaceTestCase extends EventTestCase {
  def event: Event = {
    AddedToSpaceEvent(
      Instant.parse("2017-03-02T19:02:59.910959Z"),
      Space(
        "spaces/AAAAAAAAAAA",
        "Chuck Norris Discussion Room",
        SpaceType.Room
      ),
      None,
      User(
        "users/12345678901234567890",
        "Chuck Norris",
        UserType.Human,
        "https://lh3.googleusercontent.com/.../photo.jpg"
      )
    )
  }
}
