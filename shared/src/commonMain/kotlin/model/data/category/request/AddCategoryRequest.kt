package model.data.category.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddCategoryRequest(
    @SerialName("title") val title: String,
    @SerialName("icon") val icon: Int
)
