package model.domain

data class Token(
    val token: String,
    val userId: String,
    val isAdmin: Boolean
)
