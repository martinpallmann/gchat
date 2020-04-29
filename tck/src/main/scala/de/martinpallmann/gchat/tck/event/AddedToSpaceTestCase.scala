package de.martinpallmann.gchat.tck.event

import java.time.Instant

import de.martinpallmann.gchat.Event
import de.martinpallmann.gchat.tck.EventTestCase

class AddedToSpaceTestCase extends EventTestCase {
  def event: Event = {
    import de.martinpallmann.gchat.event._
    Event.AddedToSpace(
      Instant.parse("2017-03-02T19:02:59.910959Z"),
      Space.Room("spaces/AAAAAAAAAAA", "Chuck Norris Discussion Room"),
      None,
      User(
        "https://lh3.googleusercontent.com/.../photo.jpg",
        "Chuck Norris",
        "users/12345678901234567890",
        "chuck@example.com"
      )
    )
  }
}
