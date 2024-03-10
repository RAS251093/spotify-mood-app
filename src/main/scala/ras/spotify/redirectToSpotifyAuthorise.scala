package ras.spotify

import org.scalajs.dom
import org.scalajs.dom.{Headers, HttpMethod, RequestInit, URL, URLSearchParams, fetch}
import ras.spotify.model.{AuthResponse, UserProfile}

import scala.util.Random
import scala.concurrent._
import scala.scalajs.js
import scala.scalajs.js.typedarray.byteArray2Int8Array
import ExecutionContext.Implicits.global
import scala.scalajs.js.JSON

case object redirectToSpotifyAuthorise {
  val ClientId = "c597644c918c4f008e1cb6f073a7b4fc"
  val RedirectUri = "http://localhost:63342/callback"
  val Scope = "user-read-private user-read-email"
  val AuthUrl = new URL("https://accounts.spotify.com/authorize")

  private def redirectToAuthCodeFLow(ClientId: String): String = {
    val verifier = generateCodeVerifier()
    val challenge = generateCodeChallenge(verifier)

    dom.window.localStorage.setItem("verifier", s"$verifier")

    val params = new URLSearchParams()
    params.append("client_id", s"$ClientId")
    params.append("response_type", "code")
    params.append("scope", s"$Scope")
    params.append("code_challenge_method", "S256")
    params.append("code_challenge", s"$challenge")
    params.append("redirect_uri", s"$RedirectUri")

    AuthUrl.search = params.toString
    dom.window.location.href = AuthUrl.toString
    val urlParams = new URLSearchParams(dom.window.location.search)
    urlParams.get("code")
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

  private def getAccessToken(ClientId: String, code: String): Unit = {
    val verifier = dom.window.localStorage.getItem("verifier")

    val params = new URLSearchParams()
    params.append("client_id", s"$ClientId")
    params.append("grant_type", "authorization_code")
    params.append("code", s"$code")
    params.append("redirect_uri", s"$RedirectUri")
    params.append("code_verifier", s"$verifier")

    val result = fetch(
      "https://accounts.spotify.com/api/token",
      new RequestInit {
        method = HttpMethod.POST
        headers = new Headers {
          js.Array(
            js.Array("Content-Type", "application/x-www-form-urlencoded")
          )
        }
        body = params
      }
    )
    val accessTokenRes = result.toFuture.toString
    val accessToken = js.JSON.parse(accessTokenRes).asInstanceOf[AuthResponse].AccessTokenResponse
    dom.window.localStorage.setItem("access_token", accessToken)
  }

  private def fetchProfile(): Unit = {
    val token = dom.window.localStorage.getItem("access_token")

    val result = fetch(
      "https://api.spotify.com/v1/me",
      new RequestInit {
        method = HttpMethod.GET
        headers = new Headers {
          js.Array(
            js.Array("Authorization", s"Bearer ${token}")
          )
        }
      }
    )

    val profileRes = result.toFuture.toString
    val profile = js.JSON.parse(profileRes).asInstanceOf[UserProfile]
  }

  def updateUI(): Unit = {
    val urlCode = redirectToAuthCodeFLow(ClientId)
    println(urlCode)
    getAccessToken(ClientId, urlCode)
    val profile = fetchProfile()
    println(profile)
  }
}
