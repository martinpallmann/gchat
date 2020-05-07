package de.martinpallmann.gchat.tck.request

import java.time.Instant

import de.martinpallmann.gchat.BotRequest
import de.martinpallmann.gchat.gen._
import de.martinpallmann.gchat.tck.BotRequestTestCase

class CardClickedTestCase extends BotRequestTestCase {
  def request: BotRequest =
    BotRequest.CardClicked(
      Instant.parse("2017-11-14T01:44:58.521823Z"),
      Space(
        name = "spaces/AAAAtZLKDkk",
        displayName = "Testing Room",
        `type` = SpaceType.Room
      ),
      Message(
        name = "spaces/AAAAtZLKDkk/messages/e3fCf-i1PXE.8OGDcWT2HwI",
        sender = User(
          name = "users/118066814328248020034",
          displayName = "Test Bot",
          `type` = UserType.Bot
        ),
        createTime = Instant.parse("2017-11-14T01:44:58.521823Z"),
        space = Space(
          name = "spaces/AAAAtZLKDkk",
          `type` = SpaceType.Room,
          displayName = "Testing Room"
        ),
        thread = Thread(name = "spaces/AAAAtZLKDkk/threads/e3fCf-i1PXE")
      ),
      User(
        name = "users/102651148563033885715",
        displayName = "Geordi La Forge",
        `type` = UserType.Human
      ),
      FormAction(
        actionMethodName = "upvote",
        parameters = List(
          ActionParameter(key = "count", value = "7"),
          ActionParameter(key = "id", value = "123456")
        )
      )
    )
}
