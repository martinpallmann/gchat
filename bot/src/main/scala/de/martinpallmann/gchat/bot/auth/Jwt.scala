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

package de.martinpallmann.gchat.bot.auth

import java.security.interfaces.{RSAPrivateKey, RSAPublicKey}

import cats.effect.IO
import com.auth0.jwt.{JWT, JWTVerifier}
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.RSAKeyProvider

import scala.concurrent.duration._
import scala.util.Try

class Jwt(googleProjectNo: String) {

  private val url =
    "https://www.googleapis.com/service_accounts/v1/metadata/x509/" +
      "chat@system.gserviceaccount.com"

  private val verification = for {
    st <- PublicKeyStorage(url)
  } yield JWT.require(Algorithm.RSA256(keyProvider(st)))

  private val verifier: IO[JWTVerifier] =
    verification.map(
      _.acceptLeeway(5.minutes.toSeconds)
        .withIssuer("chat@system.gserviceaccount.com")
        .withAudience(googleProjectNo)
        .build()
    )

  def verify(token: String): IO[Boolean] =
    verifier.map(
      x =>
        Try {
          x.verify(token)
        }.isSuccess
    )

  def keyProvider(storage: PublicKeyStorage): RSAKeyProvider =
    new RSAKeyProvider {

      def getPublicKeyById(keyId: String): RSAPublicKey =
        storage.get(keyId).orNull

      def getPrivateKey: RSAPrivateKey =
        null

      def getPrivateKeyId: String =
        null
    }
}

object Jwt {
  def apply(googleProjectNo: String): Jwt = new Jwt(googleProjectNo)
}
