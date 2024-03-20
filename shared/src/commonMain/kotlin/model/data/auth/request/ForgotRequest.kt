package model.data.auth.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForgotRequest(
    @SerialName("email") val email: String,
    @SerialName("newPassword") val newPassword: String,
    @SerialName("repeatNewPassword") val repeatNewPassword: String
)
