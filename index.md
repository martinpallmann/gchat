---
title: Quickstart
toc: true
---

## Overview

Gchat is a bot creation framework for chat bots that talk to the google hangouts api.
At the moment it only supports messages that are sent synchronously e.g. 
messages as a response to a user initiated event.
It can be included in your build by adding this to your `build.sbt`:

```
libraryDependencies += "de.martinpallmann.gchat" %% "gchat-bot" % "0.0.23"
```

Then all you have to do is to implement the `Bot` trait.

If you run the following code it will start a http server (powered by [http4s](https://http4s.org)) on port 9000.
You can change the port by setting the environment variable named `PORT` to another value.
It listens to incoming post-requests on the root path.

```scala
import java.time.Instant

import de.martinpallmann.gchat.Bot
import de.martinpallmann.gchat.BotResponse
import de.martinpallmann.gchat.gen.{
  Message, 
  Space, 
  User,
  FormAction
}

object Main extends Bot {
  def onAddedToSpace(
      eventTime: Instant,
      space: Space,
      user: User): Message = 
    BotResponse.text(
      "Thanks for adding me to the space."
    )

  def onRemovedFromSpace(
      eventTime: Instant, 
      space: Space, 
      user: User): Unit =
    {}

  def onMessageReceived(
      eventTime: Instant,
      space: Space,
      message: Message,
      user: User): Message =
    BotResponse.text(
      "Got a message."
    )

  def onCardClicked(
      eventTime: Instant,
      space: Space,
      message: Message,
      user: User,
      action: FormAction): Message =
    BotResponse.text(
      "You clicked a card."
    )
}
```

To test the server you can run the following curl command:

```
curl localhost:9000 -XPOST -d '{
    "type": "ADDED_TO_SPACE",
    "eventTime": "2017-03-02T19:02:59.910959Z",
    "space": {
        "name": "spaces/AAAAAAAAAAA",
        "displayName": "Chuck Norris Discussion Room",
        "type": "ROOM"
    },
    "user": {
        "name": "users/12345678901234567890",
        "displayName": "Chuck Norris",
        "avatarUrl": "https://example.com/photo.jpg",
        "email": "chuck@example.com"
    }
}'
```

More documentation about the message format can be found here: 
https://developers.google.com/hangouts/chat/reference
