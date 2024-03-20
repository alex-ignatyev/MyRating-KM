package model.domain

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Long,
    val title: String,
    val icon: Int
)
