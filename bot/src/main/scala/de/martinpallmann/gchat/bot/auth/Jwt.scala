package de.martinpallmann.gchat.bot.auth

import java.security.interfaces.{RSAPrivateKey, RSAPublicKey}

import cats.effect.IO
import com.auth0.jwt.{JWT, JWTVerifier}
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.RSAKeyProvider

import scala.concurrent.duration._
import scala.util.Try

object Jwt {

  private val url =
    "https://www.googleapis.com/service_accounts/v1/metadata/x509/" +
      "chat@system.gserviceaccount.com"

  private val verification = for {
    st <- PublicKeyStorage(url)
  } yield JWT.require(Algorithm.RSA256(Jwt.keyProvider(st)))

  private val verifier: IO[JWTVerifier] =
    verification.map(
      _.acceptLeeway(5.minutes.toSeconds)
        .withIssuer("chat@system.gserviceaccount.com")
        .withAudience("301972490637")
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
