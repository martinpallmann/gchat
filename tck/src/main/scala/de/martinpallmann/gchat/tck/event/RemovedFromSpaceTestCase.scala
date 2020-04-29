package de.martinpallmann.gchat.tck.event

import java.time.Instant

import de.martinpallmann.gchat.Event
import de.martinpallmann.gchat.tck.EventTestCase

class RemovedFromSpaceTestCase extends EventTestCase {
  def event: Event = {
    import de.martinpallmann.gchat.event._
    Event.RemovedFromSpace(
      Instant.parse("2017-03-02T19:02:59.910959Z"),
      Space.Dm("spaces/AAAAAAAAAAA"),
      User(
        "https://lh3.googleusercontent.com/.../photo.jpg",
        "Chuck Norris",
        "users/12345678901234567890",
        "chuck@example.com"
      )
    )
  }
}
