package model.domain

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Long,
    val title: String,
    val rate: Int
)
