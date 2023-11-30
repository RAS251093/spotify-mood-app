package ras.spotify.model

import spray.json.DefaultJsonProtocol.StringJsonFormat
import spray.json.{JsValue, RootJsonFormat}

case class ExtUrlsObj(spotifyUrl: Option[String])

object ExtUrlsObj{
  implicit val jsonFormat: RootJsonFormat[ExtUrlsObj] =
    new RootJsonFormat[ExtUrlsObj] {
      override def write(obj: ExtUrlsObj): JsValue = ???

      override def read(json: JsValue): ExtUrlsObj = {
        val fields = json.asJsObject().fields
        ExtUrlsObj(
          spotifyUrl = fields.get("spotify").map(json => json.convertTo[String])
        )
      }
    }
}
