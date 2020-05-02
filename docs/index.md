# Google Chat API

add `libraryDependencies += "de.martinpallmann.gchat" %% "gchat-bot" % "0.0.17"`
do your build.sbt and implement the `de.martinpallmann.gchat.bot.Bot` trait.

Like so, maybe:

```scala
import java.time.Instant

import de.martinpallmann.gchat.BotResponse
import de.martinpallmann.gchat.bot.Bot
import de.martinpallmann.gchat.gen.{Message, Space, User}

object Main extends Bot {
  def onAddedToSpace(eventTime: Instant,
                     space: Space,
                     user: User): BotResponse = BotResponse.Empty

  def onRemovedFromSpace(eventTime: Instant, space: Space, user: User): Unit = {}

  def onMessageReceived(eventTime: Instant,
                        space: Space,
                        message: Message,
                        user: User): BotResponse = BotResponse.Empty
}

```

that should be everything you need to get started.
