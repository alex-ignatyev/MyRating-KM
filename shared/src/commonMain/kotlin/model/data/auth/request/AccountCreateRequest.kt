package model.data.auth.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountCreateRequest(
    @SerialName("login") val login: String,
    @SerialName("name") val name: String,
    @SerialName("password") val password: String,
    @SerialName("repeatPassword") val repeatPassword: String
)
