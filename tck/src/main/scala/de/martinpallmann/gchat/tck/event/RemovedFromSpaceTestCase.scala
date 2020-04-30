package de.martinpallmann.gchat.tck.event

import java.time.Instant

import de.martinpallmann.gchat.Event.RemovedFromSpaceEvent
import de.martinpallmann.gchat.{Event, Space, SpaceType, User, UserType}
import de.martinpallmann.gchat.tck.EventTestCase

class RemovedFromSpaceTestCase extends EventTestCase {
  def event: Event = {
    RemovedFromSpaceEvent(
      Instant.parse("2017-03-02T19:02:59.910959Z"),
      Space("spaces/AAAAAAAAAAA", "", SpaceType.Dm),
      User(
        "users/12345678901234567890",
        "Chuck Norris",
        UserType.Human,
        "https://lh3.googleusercontent.com/.../photo.jpg",
      ),
    )
  }
}
