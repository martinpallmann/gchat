package de.martinpallmann.gchat.example.sys

import org.slf4j.LoggerFactory

object Env {

  private val logger = LoggerFactory.getLogger(getClass)

  def withLog[A](o: Option[A])(log: => String): Option[A] =
    o match {
      case s @ Some(_) => s
      case n @ None    => logger.debug(log); n
    }

  def withDefault[A](key: String, fallback: A)(f: String => Option[A]): A =
    (for {
      v <- sys.env.get(key)
      a <- withLog(f(v))(s"could not convert string to '${fallback.getClass}'")
    } yield a).getOrElse(fallback)

  def apply(key: String): Option[String] =
    withLog(sys.env.get(key))(s"could not retrieve env var: '$key'")
}
