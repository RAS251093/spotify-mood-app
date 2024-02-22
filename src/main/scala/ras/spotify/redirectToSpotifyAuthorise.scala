package ras.spotify

import org.scalajs.dom
import org.scalajs.dom.{URL, URLSearchParams, document}

import scala.util.Random
import scala.concurrent._
import scala.scalajs.js
import scala.scalajs.js.typedarray.byteArray2Int8Array

case object redirectToSpotifyAuthorise {
  val ClientId = "c597644c918c4f008e1cb6f073a7b4fc"
  val RedirectUri = "http://localhost:63342/spotify-mood-app/ras/index.html?_ijt=l6pjm8gb23sbi4o1q6ev20a8q4&_ij_reload=RELOAD_ON_SAVE"
  val Scope = "user-read-private user-read-email"
  val AuthUrl = new URL("https://accounts.spotify.com/authorize")

  private def redirectToAuthCodeFLow(ClientId: String): Unit = {
    val verifier = generateCodeVerifier()
    println("verifier is: " + verifier)
    val challenge = generateCodeChallenge(verifier)
    println("challenge is " + "encrypted: " + challenge + " decrypted: " + dom.window.atob(challenge))

    dom.window.localStorage.setItem("verifier", verifier)

    val params = Map(
      "response_type" -> "code",
      "client_id" -> s"$ClientId",
      "scope" -> s"$Scope",
      "code_challenge_method" -> "5256",
      "code_challenge" -> s"$challenge",
      "redirect_uri" -> s"$RedirectUri"
    )

    //authUrl.search = new URLSearchParams(params).toString();
    //window.location.href = authUrl.toString();
    AuthUrl.search = new URLSearchParams()


  }

  private def generateCodeVerifier(): String = {
    val codeLength = 128
    Random.alphanumeric.take(codeLength).mkString
  }

  private def generateCodeChallenge(codeVerifier: String)= {
   val data = codeVerifier.getBytes()
   val sha256digest = dom.crypto.subtle.digest("SHA-256", byteArray2Int8Array(data)).toFuture
   base64encode(sha256digest)
  }

  private def base64encode(hashedData: Future[js.Any]): String = {
    dom.window.btoa(hashedData.toString)
      .replaceAll("/=/g", "")
      .replaceAll("/+/g", "-")
      .replaceAll("///g", "_")
  }

  private def getAccessToken(ClientId: String, code: String): Future[String] = {
    //TODO: Get access token for code
  }

  private def fetchProfile(token: String): Future[Unit] = {
    //TODO: Call web API
  }

  def updateUI(): Unit = {
    redirectToAuthCodeFLow(ClientId)
  }
}
