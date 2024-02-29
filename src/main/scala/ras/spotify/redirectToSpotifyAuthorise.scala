package ras.spotify

import org.scalajs.dom
import org.scalajs.dom.idb.Request
import org.scalajs.dom.{Headers, HttpMethod, RequestInit, URL, URLSearchParams, document, fetch, window}

import scala.util.Random
import scala.concurrent._
import scala.scalajs.js
import scala.scalajs.js.typedarray.byteArray2Int8Array

case object redirectToSpotifyAuthorise {
  val ClientId = "c597644c918c4f008e1cb6f073a7b4fc"
  val RedirectUri = "http://localhost:63342/spotify-mood-app/ras/index.html"
  val Scope = "user-read-private user-read-email"
  val AuthUrl = new URL("https://accounts.spotify.com/authorize")

  private def redirectToAuthCodeFLow(ClientId: String): Unit = {
    val verifier = generateCodeVerifier()
    val challenge = generateCodeChallenge(verifier)

    dom.window.localStorage.setItem("verifier", verifier)

    val params = new URLSearchParams()
    params.append("client_id", s"$ClientId")
    params.append("response_type", "code")
    params.append("scope", s"$Scope")
    params.append("code_challenge_method", "S256")
    params.append("code_challenge", s"$challenge")
    params.append("redirect_uri", s"$RedirectUri")

    AuthUrl.search = params.toString
    window.location.href = AuthUrl.toString
    val urlParams = new URLSearchParams(window.location.search)
    val urlCode = urlParams.get("code")
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

/*  private def getAccessToken(ClientId: String, code: String): Future[String] = {
    //TODO: Get access token for code
  }

  private def fetchProfile(token: String): Future[Unit] = {
    //TODO: Call web API
  }*/

  def updateUI(): Unit = {
    redirectToAuthCodeFLow(ClientId)
  }
}
