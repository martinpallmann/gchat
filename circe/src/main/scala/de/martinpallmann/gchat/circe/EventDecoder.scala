package de.martinpallmann.gchat.circe

import java.time.Instant
import de.martinpallmann.gchat.gen._
import de.martinpallmann.gchat.Event
import io.circe._
import io.circe.generic.semiauto._

import scala.util.Try

trait EventDecoder {

  implicit val decodeInstant: Decoder[Instant] =
    Decoder.decodeString.emapTry(s => Try(Instant.parse(s)))

  implicit val decodeSpaceType: Decoder[SpaceType] =
    Decoder.decodeString.emap {
      case "DM"   => Right(SpaceType.Dm)
      case "ROOM" => Right(SpaceType.Room)
      case x      => Left(s"unkown space type: $x")
    }

  implicit val decodeSpace: Decoder[Space] =
    deriveDecoder[Space]

  implicit val decodeTextParagraph: Decoder[TextParagraph] =
    deriveDecoder[TextParagraph]

  implicit val decodeKeyValueIcon: Decoder[KeyValueIcon] =
    Decoder.decodeString.emap {
      case "AIRPLANE" => Right(KeyValueIcon.Airplane)
      case "BOOKMARK" => Right(KeyValueIcon.Bookmark)
      case "BUS"      => Right(KeyValueIcon.Bus)
      case "CAR"      => Right(KeyValueIcon.Car)
      case "CLOCK"    => Right(KeyValueIcon.Clock)
      case "CONFIRMATION_NUMBER_ICON" =>
        Right(KeyValueIcon.ConfirmationNumberIcon)
      case "DOLLAR"           => Right(KeyValueIcon.Dollar)
      case "DESCRIPTION"      => Right(KeyValueIcon.Description)
      case "EMAIL"            => Right(KeyValueIcon.Email)
      case "EVENT_PERFORMER"  => Right(KeyValueIcon.EventPerformer)
      case "EVENT_SEAT"       => Right(KeyValueIcon.EventSeat)
      case "FLIGHT_ARRIVAL"   => Right(KeyValueIcon.FlightArrival)
      case "FLIGHT_DEPARTURE" => Right(KeyValueIcon.FlightDeparture)
      case "HOTEL"            => Right(KeyValueIcon.Hotel)
      case "HOTEL_ROOM_TYPE"  => Right(KeyValueIcon.HotelRoomType)
      case "INVITE"           => Right(KeyValueIcon.Invite)
      case "MAP_PIN"          => Right(KeyValueIcon.MapPin)
      case "MEMBERSHIP"       => Right(KeyValueIcon.Membership)
      case "MULTIPLE_PEOPLE"  => Right(KeyValueIcon.MultiplePeople)
      case "OFFER"            => Right(KeyValueIcon.Offer)
      case "PERSON"           => Right(KeyValueIcon.Person)
      case "PHONE"            => Right(KeyValueIcon.Phone)
      case "RESTAURANT_ICON"  => Right(KeyValueIcon.RestaurantIcon)
      case "SHOPPING_CART"    => Right(KeyValueIcon.ShoppingCart)
      case "STAR"             => Right(KeyValueIcon.Star)
      case "STORE"            => Right(KeyValueIcon.Store)
      case "TICKET"           => Right(KeyValueIcon.Ticket)
      case "TRAIN"            => Right(KeyValueIcon.Train)
      case "VIDEO_CAMERA"     => Right(KeyValueIcon.VideoCamera)
      case "VIDEO_PLAY"       => Right(KeyValueIcon.VideoPlay)
      case x                  => Left(s"unkwon KeyValueIcon: $x")
    }

  implicit val decodeKeyValue: Decoder[KeyValue] =
    deriveDecoder[KeyValue]

  implicit val decodeImage: Decoder[Image] =
    deriveDecoder[Image]

  implicit val decodeOpenLink: Decoder[OpenLink] =
    deriveDecoder[OpenLink]

  implicit val decodeActionParameter: Decoder[ActionParameter] =
    deriveDecoder[ActionParameter]

  implicit val decodeFormAction: Decoder[FormAction] =
    deriveDecoder[FormAction]

  implicit val decodeOnClick: Decoder[OnClick] =
    deriveDecoder[OnClick]

  implicit val decodeTextButton: Decoder[TextButton] =
    deriveDecoder[TextButton]

  implicit val decodeImageButtonIcon: Decoder[ImageButtonIcon] =
    Decoder.decodeString.emap {
      case "AIRPLANE" => Right(ImageButtonIcon.Airplane)
      case "BOOKMARK" => Right(ImageButtonIcon.Bookmark)
      case "BUS"      => Right(ImageButtonIcon.Bus)
      case "CAR"      => Right(ImageButtonIcon.Car)
      case "CLOCK"    => Right(ImageButtonIcon.Clock)
      case "CONFIRMATION_NUMBER_ICON" =>
        Right(ImageButtonIcon.ConfirmationNumberIcon)
      case "DOLLAR"           => Right(ImageButtonIcon.Dollar)
      case "DESCRIPTION"      => Right(ImageButtonIcon.Description)
      case "EMAIL"            => Right(ImageButtonIcon.Email)
      case "EVENT_PERFORMER"  => Right(ImageButtonIcon.EventPerformer)
      case "EVENT_SEAT"       => Right(ImageButtonIcon.EventSeat)
      case "FLIGHT_ARRIVAL"   => Right(ImageButtonIcon.FlightArrival)
      case "FLIGHT_DEPARTURE" => Right(ImageButtonIcon.FlightDeparture)
      case "HOTEL"            => Right(ImageButtonIcon.Hotel)
      case "HOTEL_ROOM_TYPE"  => Right(ImageButtonIcon.HotelRoomType)
      case "INVITE"           => Right(ImageButtonIcon.Invite)
      case "MAP_PIN"          => Right(ImageButtonIcon.MapPin)
      case "MEMBERSHIP"       => Right(ImageButtonIcon.Membership)
      case "MULTIPLE_PEOPLE"  => Right(ImageButtonIcon.MultiplePeople)
      case "OFFER"            => Right(ImageButtonIcon.Offer)
      case "PERSON"           => Right(ImageButtonIcon.Person)
      case "PHONE"            => Right(ImageButtonIcon.Phone)
      case "RESTAURANT_ICON"  => Right(ImageButtonIcon.RestaurantIcon)
      case "SHOPPING_CART"    => Right(ImageButtonIcon.ShoppingCart)
      case "STAR"             => Right(ImageButtonIcon.Star)
      case "STORE"            => Right(ImageButtonIcon.Store)
      case "TICKET"           => Right(ImageButtonIcon.Ticket)
      case "TRAIN"            => Right(ImageButtonIcon.Train)
      case "VIDEO_CAMERA"     => Right(ImageButtonIcon.VideoCamera)
      case "VIDEO_PLAY"       => Right(ImageButtonIcon.VideoPlay)
      case x                  => Left(s"unkwon ImageButtonIcon: $x")
    }

  implicit val decodeImageButton: Decoder[ImageButton] =
    deriveDecoder[ImageButton]

  implicit val decodeButton: Decoder[Button] =
    deriveDecoder[Button]

  implicit val decodeWidgetMarkup: Decoder[WidgetMarkup] =
    deriveDecoder[WidgetMarkup]

  implicit val decodeSection: Decoder[Section] =
    deriveDecoder[Section]

  implicit val decodeCardAction: Decoder[CardAction] =
    deriveDecoder[CardAction]

  implicit val decodeCardHeaderImageStyle: Decoder[CardHeaderImageStyle] =
    Decoder.decodeString.emap {
      case "AVATAR" => Right(CardHeaderImageStyle.Avatar)
      case "IMAGE"  => Right(CardHeaderImageStyle.Image)
      case x        => Left(s"unkown card header image style: $x")
    }

  implicit val decodeCardHeader: Decoder[CardHeader] =
    deriveDecoder[CardHeader]

  implicit val decodeCard: Decoder[Card] =
    deriveDecoder[Card]

  implicit val decodeActionResponseType: Decoder[ActionResponseType] =
    Decoder.decodeString.emap {
      case "NEW_MESSAGE"    => Right(ActionResponseType.NewMessage)
      case "REQUEST_CONFIG" => Right(ActionResponseType.RequestConfig)
      case "UPDATE_MESSAGE" => Right(ActionResponseType.UpdateMessage)
      case x                => Left(s"unkown action response type: $x")
    }

  implicit val decodeThread: Decoder[Thread] =
    deriveDecoder[Thread]

  implicit val decodeUserMentionMetadataType: Decoder[UserMentionMetadataType] =
    deriveDecoder[UserMentionMetadataType]

  implicit val decodeUserMentionMetadata: Decoder[UserMentionMetadata] =
    deriveDecoder[UserMentionMetadata]

  implicit val decodeAnnotationType: Decoder[AnnotationType] =
    Decoder.decodeString.emap {
      case "USER_MENTION" => Right(AnnotationType.UserMention)
      case x              => Left(s"unkown annotation type: $x")
    }

  implicit val decodeAnnotation: Decoder[Annotation] =
    deriveDecoder[Annotation]

  implicit val decodeActionResponse: Decoder[ActionResponse] =
    deriveDecoder[ActionResponse]

  implicit val decodeMessage: Decoder[Message] =
    deriveDecoder[Message]

  implicit val decodeUserType: Decoder[UserType] = Decoder.decodeString.emap {
    case "BOT"   => Right(UserType.Bot)
    case "HUMAN" => Right(UserType.Human)
    case x       => Left(s"unknown user type: $x")
  }

  implicit val decodeUser: Decoder[User] =
    deriveDecoder[User]

  implicit val decodeEventAdded: Decoder[Event.AddedToSpaceEvent] =
    deriveDecoder[Event.AddedToSpaceEvent]

  implicit val decodeEventMessage: Decoder[Event.MessageEvent] =
    deriveDecoder[Event.MessageEvent]

  implicit val decodeEventRemoved: Decoder[Event.RemovedFromSpaceEvent] =
    deriveDecoder[Event.RemovedFromSpaceEvent]

  implicit val decodeEvent: Decoder[Event] = (c: HCursor) =>
    for {
      t <- c.downField("type").as[String]
      r <- t match {
        case "ADDED_TO_SPACE"     => decodeEventAdded(c)
        case "MESSAGE"            => decodeEventMessage(c)
        case "REMOVED_FROM_SPACE" => decodeEventRemoved(c)
        case x                    => Left(DecodingFailure(s"unknown type: $x", c.history))
      }
    } yield r
}
