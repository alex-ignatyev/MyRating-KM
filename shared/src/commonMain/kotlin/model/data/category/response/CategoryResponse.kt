package model.data.category.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    @SerialName("id") val id: Long?,
    @SerialName("title") val title: String?,
    @SerialName("icon") val icon: Int?
)
