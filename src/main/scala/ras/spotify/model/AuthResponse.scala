package ras.spotify.model

import spray.json.DefaultJsonProtocol.{IntJsonFormat, StringJsonFormat}
import spray.json.{JsValue, RootJsonFormat}

case class AuthResponse(
                         AccessTokenResponse: String,
                         TokenType: Option[String],
                         Scope: Option[String],
                         ExpiresIn: Option[Int],
                         RefreshToken: Option[String]
                       )

object AuthResponse {
  implicit val jsonFormat: RootJsonFormat[AuthResponse] =
    new RootJsonFormat[AuthResponse] {
      override def write(obj: AuthResponse): JsValue = ???

      override def read(json: JsValue): AuthResponse = {
        val fields = json.asJsObject().fields
        AuthResponse(
          AccessTokenResponse = fields("access_token").convertTo[String],
          TokenType = fields.get("token_type").map(json => json.convertTo[String]),
          Scope = fields.get("scope").map(json => json.convertTo[String]),
          ExpiresIn = fields.get("expires_in").map(json => json.convertTo[Int]),
          RefreshToken = fields.get("refresh_token").map(json => json.convertTo[String])
        )
      }
    }
}