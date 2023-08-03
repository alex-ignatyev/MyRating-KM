package model.data.tobacco

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TobaccoInfoRequest(
    @SerialName("tobaccoId")  val tobaccoId: String
)
