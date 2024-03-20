package model.data.category.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteCategoryRequest(
    @SerialName("categoryId") val categoryId: Long
)
