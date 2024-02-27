package model.domain

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: String,
    val title: String
)
