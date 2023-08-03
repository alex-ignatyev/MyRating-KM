package model.data.auth.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountLoginRequest(
    @SerialName("login") val login: String,
    @SerialName("password") val password: String
)
