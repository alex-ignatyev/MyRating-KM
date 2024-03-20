package model.data.product.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteProductRequest(
    @SerialName("categoryId") val categoryId: Long,
    @SerialName("productId") val productId: Long
)
