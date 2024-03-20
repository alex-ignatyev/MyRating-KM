package model.data.product.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProductRequest(
    @SerialName("categoryId") val categoryId: Long,
    @SerialName("productId") val productId: Long,
    @SerialName("newTitle") val newTitle: String,
    @SerialName("newRate") val newRate: Int
)
