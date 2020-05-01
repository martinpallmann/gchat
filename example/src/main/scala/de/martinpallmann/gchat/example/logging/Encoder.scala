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

package de.martinpallmann.gchat.example.logging

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.Layout
import ch.qos.logback.core.encoder.{LayoutWrappingEncoder, Encoder => Enc}

object Encoder {
  def apply(
    layout: Layout[ILoggingEvent],
  )(implicit lc: LoggerContext,
  ): Enc[ILoggingEvent] = {
    val result = new LayoutWrappingEncoder[ILoggingEvent]
    result.setContext(lc)
    result.setLayout(layout)
    result
  }
}
