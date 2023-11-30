package ras.spotify.model

import spray.json.DefaultJsonProtocol._
import spray.json.{JsValue, RootJsonFormat}

case class FollowersObj(href: Option[String], total: Option[Int])

object FollowersObj{
  implicit val jsonFormat: RootJsonFormat[FollowersObj] =
    new RootJsonFormat[FollowersObj] {
      override def write(obj: FollowersObj): JsValue = ???

      override def read(json: JsValue): FollowersObj = {
        val fields = json.asJsObject().fields
        FollowersObj(
          href = fields.get("isrc").map(json => json.convertTo[String]),
          total = fields.get("ean").map(json => json.convertTo[Int]),
        )
      }
    }
}


