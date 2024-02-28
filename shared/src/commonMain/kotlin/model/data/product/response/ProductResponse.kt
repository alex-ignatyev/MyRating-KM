package model.data.product.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    @SerialName("id") val id: Long?,
    @SerialName("title") val title: String?,
    @SerialName("rate") val rate: Int?
)
