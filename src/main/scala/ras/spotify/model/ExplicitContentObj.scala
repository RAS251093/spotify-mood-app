package ras.spotify.model

import spray.json.DefaultJsonProtocol.BooleanJsonFormat
import spray.json.{JsValue, RootJsonFormat}

case class ExplicitContentObj(filterEnabled: Option[Boolean], filterLocked: Option[Boolean])

object ExplicitContentObj {
  implicit val jsonFormat: RootJsonFormat[ExplicitContentObj] =
    new RootJsonFormat[ExplicitContentObj] {
      override def write(obj: ExplicitContentObj): JsValue = ???

      override def read(json: JsValue): ExplicitContentObj = {
        val fields = json.asJsObject().fields
        ExplicitContentObj(
          filterEnabled = fields.get("filter_enabled").map(json => json.convertTo[Boolean]),
          filterLocked = fields.get("filter_locked").map(json => json.convertTo[Boolean])
        )
      }
    }
}
