package model.domain

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    val login: String,
    val email: String,
    val phone: String
)
