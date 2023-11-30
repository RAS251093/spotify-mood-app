package ras.spotify.model

import spray.json.DefaultJsonProtocol.StringJsonFormat
import spray.json.{JsValue, RootJsonFormat}

case class UserProfile(
                      country: Option[String],
                      displayName: Option[String],
                      email: Option[String],
                      explicitContent: Option[ExplicitContentObj],
                      externalUrls: Option[ExtUrlsObj],
                      followers: Option[FollowersObj],
                      href: Option[String],
                      id: Option[String],
                      images: Option[ImgObj],
                      product: Option[String],
                      objectType: Option[String],
                      uri: Option[String]
                      )

object UserProfile {
  implicit val jsonFormat: RootJsonFormat[UserProfile] =
    new RootJsonFormat[UserProfile] {
      override def write(obj: UserProfile): JsValue = ???

      override def read(json: JsValue): UserProfile = {
        val fields = json.asJsObject().fields
        UserProfile(
          country = fields.get("country").map(json => json.convertTo[String]),
          displayName = fields.get("display_name").map(json => json.convertTo[String]),
          email = fields.get("email").map(json => json.convertTo[String]),
          explicitContent = fields.get("explicit_content").map(json => json.convertTo[ExplicitContentObj]),
          externalUrls = fields.get("external_urls").map(json => json.convertTo[ExtUrlsObj]),
          followers = fields.get("followers").map(json => json.convertTo[FollowersObj]),
          href = fields.get("href").map(json => json.convertTo[String]),
          id = fields.get("id").map(json => json.convertTo[String]),
          images = fields.get("images").map(json => json.convertTo[ImgObj]),
          product = fields.get("product").map(json => json.convertTo[String]),
          objectType = fields.get("type").map(json => json.convertTo[String]),
          uri = fields.get("uri").map(json => json.convertTo[String])
        )
      }
    }
}
