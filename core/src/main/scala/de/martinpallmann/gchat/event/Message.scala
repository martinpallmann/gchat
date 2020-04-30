package de.martinpallmann.gchat.event

import java.time.Instant

case class Message(name: String,
                   sender: User,
                   createTime: Instant,
                   text: String,
                   thread: Thread,
                   annotations: Option[List[Annotation]],
                   argumentText: String)
