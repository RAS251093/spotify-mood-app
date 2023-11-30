package ras.spotify.model

import spray.json.DefaultJsonProtocol._
import spray.json.{JsValue, RootJsonFormat}

case class ImgObj(imgUrl: Option[String], imgHeight: Option[Int], imgWidth: Option[Int])

object ImgObj{
  implicit val jsonFormat: RootJsonFormat[ImgObj] =
    new RootJsonFormat[ImgObj] {
      override def write(obj: ImgObj): JsValue = ???

      override def read(json: JsValue): ImgObj = {
        val fields = json.asJsObject().fields
        ImgObj(
          imgUrl = fields.get("url").map(json => json.convertTo[String]),
          imgHeight = fields.get("height").map(json => json.convertTo[Int]),
          imgWidth = fields.get("width").map(json => json.convertTo[Int])
        )
      }
    }
}
