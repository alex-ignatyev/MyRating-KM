package model.data.tobacco

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TobaccoFeedResponse(
    @SerialName("id") val id: String?,
    @SerialName("taste") val taste: String?,
    @SerialName("company") val company: String?,
    @SerialName("line") val line: String?,
    @SerialName("image") var image: String?,
    @SerialName("rating") val rating: Float?,
    @SerialName("votes") val votes: Long?
)
