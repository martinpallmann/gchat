/*
 * Copyright 2020 Martin Pallmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.martinpallmann.gchat.tck

import de.martinpallmann.gchat.BotRequest
import org.scalatest.funsuite.AnyFunSuite

import scala.io.Source
import scala.util.Try

trait Tck[JSON] extends AnyFunSuite {

  def parseJson: String => Try[JSON]
  // def encode: Response => JSON
  def decode: JSON => Try[BotRequest]

//  tests("response").map(
//    t =>
//      test(s"should encode response: $t") {
//        assert(readJson("response", t) == encode(response(t)))
//    }
//  ) ++
  tests("event").map(
    t =>
      test(s"should decode event: $t") {
        // TODO: better error handling
        assert(event(t) == decode(readJson("event", t)).get)
      }
  )

  private def tests(dir: String): List[String] =
    Source
      .fromResource(s"$dir/tests.txt")
      .getLines
      .filterNot(_.trim.startsWith("#"))
      .toList

  private def readJson(dir: String, test: String): JSON = {
    val resource = s"$dir/json/$test.json"
    try {
      val src = Source.fromResource(resource)
      parseJson(src.mkString).get
    } catch {
      case e: NullPointerException =>
        throw new IllegalArgumentException(s"could not find file: $resource")
    }
  }

//  private def response(test: String): Response =
//    Reflection
//      .responseTestCase(
//        s"de.martinpallmann.gchat.tck.response.${toCamelCase(test)}"
//      )
//      .response

  private def event(test: String): BotRequest =
    Reflection
      .eventTestCase(
        s"de.martinpallmann.gchat.tck.event.${toCamelCase(test)}TestCase"
      )
      .event

  private def toCamelCase(s: String): String = {
    val toUpper: Char => Char = _.toUpper
    val toLower: Char => Char = _.toLower
    val (result, _) = s.foldLeft(("", toUpper)) {
      case ((acc, _), '_')    => (s"$acc", toUpper)
      case ((acc, modify), c) => (s"$acc${modify(c)}", toLower)
    }
    result
  }
}
