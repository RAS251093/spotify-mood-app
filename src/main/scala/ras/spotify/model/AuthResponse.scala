package ras.spotify.model

import spray.json.DefaultJsonProtocol.{IntJsonFormat, StringJsonFormat}
import spray.json.{JsValue, RootJsonFormat}

case class AuthResponse(
                         accessToken: Option[String],
                         tokenType: Option[String],
                         scope: Option[String],
                         expiresIn: Option[Int],
                         refreshToken: Option[String]
                       )

object AuthResponse {
  implicit val jsonFormat: RootJsonFormat[AuthResponse] =
    new RootJsonFormat[AuthResponse] {
      override def write(obj: AuthResponse): JsValue = ???

      override def read(json: JsValue): AuthResponse = {
        val fields = json.asJsObject().fields
        AuthResponse(
          accessToken = fields.get("access_token").map(json => json.convertTo[String]),
          tokenType = fields.get("token_type").map(json => json.convertTo[String]),
          scope = fields.get("scope").map(json => json.convertTo[String]),
          expiresIn = fields.get("expires_in").map(json => json.convertTo[Int]),
          refreshToken = fields.get("refresh_token").map(json => json.convertTo[String])
        )
      }
    }
}