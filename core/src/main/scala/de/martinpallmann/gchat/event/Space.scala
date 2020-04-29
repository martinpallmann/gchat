package de.martinpallmann.gchat.event

sealed trait Space {
  def asRoom: Option[Space.Room] = None
  def asDm: Option[Space.Dm] = None
  def fold[A](f1: Space.Room => A)(f2: Space.Dm => A): A
}

object Space {

  case class Room(name: String, displayName: String) extends Space {
    override def asRoom: Option[Space.Room] = Some(this)
    def fold[A](f1: Space.Room => A)(f2: Space.Dm => A): A = f1(this)
  }

  case class Dm(name: String) extends Space {
    override def asDm: Option[Space.Dm] = Some(this)
    def fold[A](f1: Space.Room => A)(f2: Space.Dm => A): A = f2(this)
  }
}
