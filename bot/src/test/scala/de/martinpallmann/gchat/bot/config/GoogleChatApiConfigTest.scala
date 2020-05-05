package de.martinpallmann.gchat.bot.config

import de.martinpallmann.gchat.bot.Implicits._
import java.net.URI
import minitest._

object GoogleChatApiConfigTest extends SimpleTestSuite {

  test("should read a config from a json") {
    val received = GoogleChatApiConfig.fromJson(json"""{
        "type": "1",
        "project_id": "2",
        "private_key_id": "3",
        "private_key": "4",
        "client_email": "5",
        "client_id": "6",
        "auth_uri": "https://example.com/7",
        "token_uri": "https://example.com/8",
        "auth_provider_x509_cert_url": "https://example.com/9",
        "client_x509_cert_url": "https://example.com/10"
      }""")
    val expected = Some(
      GoogleChatApiConfig(
        `type` = "1",
        projectId = "2",
        privateKeyId = "3",
        privateKey = "4",
        clientEmail = "5",
        clientId = "6",
        authUri = uri("https://example.com/7"),
        tokenUri = uri("https://example.com/8"),
        authProviderX509CertUrl = uri("https://example.com/9"),
        clientX509CertUrl = uri("https://example.com/10")
      )
    )
    assertEquals(received, expected)
  }

  def uri(s: String): URI = new URI(s)
}
