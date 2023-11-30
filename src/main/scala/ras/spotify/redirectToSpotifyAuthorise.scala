package ras.spotify

import org.scalajs.dom
import org.scalajs.dom.document

import scala.concurrent.Future
import scala.util.Random

case object redirectToSpotifyAuthorise {
  val ClientId = "c597644c918c4f008e1cb6f073a7b4fc"

  private def redirectToAuthCodeFLow(ClientId: String): Unit = {
    val verifier = generateCodeVerifier()
    val challenge = generateCodeChallenge(verifier)
  }

  private def generateCodeVerifier(): String = {
    val codeLength = 128
    Random.alphanumeric.take(codeLength).mkString
  }

  private def generateCodeChallenge(codeVerifier: String)= {
    //TODO:
    // async function generateCodeChallenge(codeVerifier: string) {
    //    const data = new TextEncoder().encode(codeVerifier);
    //    const digest = await window.crypto.subtle.digest('SHA-256', data);
    //    return btoa(String.fromCharCode.apply(null, [...new Uint8Array(digest)]))
    //        .replace(/\+/g, '-')
    //        .replace(/\//g, '_')
    //        .replace(/=+$/, '');
    // }
  }

  private def getAccessToken(ClientId: String, code: String): Unit = {
    //TODO: Get access token for code
  }

  private def fetchProfile(token: String): Unit = {
    //TODO: Call web API
  }

  def updateUI(): Unit = {
    redirectToAuthCodeFLow(ClientId)
  }
}
