package de.martinpallmann.gchat.tck.request

import java.time.Instant

import de.martinpallmann.gchat.BotRequest
import de.martinpallmann.gchat.gen._
import de.martinpallmann.gchat.gen.SpaceType.Room
import de.martinpallmann.gchat.gen.UserType.Human
import de.martinpallmann.gchat.tck.BotRequestTestCase

class AddedToSpaceTestCase extends BotRequestTestCase {
  def request: BotRequest =
    BotRequest.AddedToSpace(
      Instant.parse("2017-03-02T19:02:59.910959Z"),
      Space("spaces/AAAAAAAAAAA", "Chuck Norris Discussion Room", Room),
      None,
      User("users/12345678901234567890", "Chuck Norris", Human, "domainId")
    )
}
