package ras.ui

import org.scalajs.dom
import org.scalajs.dom.document
import ras.spotify.redirectToSpotifyAuthorise

final class BuildUI() {

/*  def addClickedMessage(): Unit = {
    appendNode(document.body, "p", "You clicked the button!")
  }*/

  def appendNode(targetNode: dom.Node, nodeType: String, text: String): Unit = {
    val newChild = document.createElement(nodeType)
    newChild.textContent = text
    targetNode.appendChild(newChild)
  }

  def setupUI(): Unit = {
    val loginButton = document.createElement("button")
    loginButton.textContent = "Log in with Spotify"
    loginButton.addEventListener("click", { (e: dom.MouseEvent) =>
      redirectToSpotifyAuthorise.updateUI()
    })
    appendNode(document.body, "H1", "Spotify Mood App")
    appendNode(document.body, "p", "Spotify Mood App looks at your song plays and predicts what kind of mood you've been in!")
    document.body.appendChild(loginButton)
  }
}
