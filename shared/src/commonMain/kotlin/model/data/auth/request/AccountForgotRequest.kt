package model.data.auth.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountForgotRequest(
    @SerialName("login") val login: String,
    @SerialName("newPassword") val newPassword: String,
    @SerialName("repeatNewPassword") val repeatNewPassword: String
)
