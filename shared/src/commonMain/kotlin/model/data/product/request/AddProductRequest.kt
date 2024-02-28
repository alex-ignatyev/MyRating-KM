package model.data.product.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddProductRequest(
    @SerialName("categoryId") val categoryId: Long,
    @SerialName("title") val title: String,
    @SerialName("rate") val rate: Int
)
