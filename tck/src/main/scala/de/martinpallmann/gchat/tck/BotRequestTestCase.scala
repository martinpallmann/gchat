package de.martinpallmann.gchat.tck

import de.martinpallmann.gchat.BotRequest

import scala.language.implicitConversions

abstract private[tck] class BotRequestTestCase {
  protected final implicit def anyToOption[A](a: A): Option[A] = Option(a)
  def request: BotRequest
  override def toString: String = request.toString
}
