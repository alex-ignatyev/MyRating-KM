package model.data.tobacco

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TobaccoInfoResponse(
    @SerialName("id") val id: String?,
    @SerialName("taste") val taste: String?,
    @SerialName("company") val company: String?,
    @SerialName("line") val line: String?,
    @SerialName("strength") val strength: Int?,

    @SerialName("image") var image: String?,

    @SerialName("strengthByUsers") val strengthByUsers: Float? = 0f,
    @SerialName("smokinessByUsers") val smokinessByUsers: Float? = 0f,
    @SerialName("aromaByUsers") val aromaByUsers: Float? = 0f,
    @SerialName("ratingByUsers") val ratingByUsers: Float? = 0f,
    @SerialName("tasteByUsers") val tasteByUsers: Float? = 0f,

    @SerialName("ratingByUser") val ratingByUser: Long?,
    @SerialName("strengthByUser") val strengthByUser: Long?,
    @SerialName("smokinessByUser") val smokinessByUser: Long?,
    @SerialName("aromaByUser") val aromaByUser: Long?,
    @SerialName("tasteByUser") val tasteByUser: Long?,

    @SerialName("votes") val votes: Long?
)
