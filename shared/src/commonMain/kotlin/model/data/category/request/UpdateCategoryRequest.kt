package model.data.category.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateCategoryRequest(
    @SerialName("categoryId") val categoryId: Long,
    @SerialName("newTitle") val newTitle: String,
    @SerialName("newIcon") val newIcon: Int
)
