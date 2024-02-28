package model.data.auth.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PasswordRequest(
    @SerialName("currentPassword") val currentPassword: String,
    @SerialName("newPassword") val newPassword: String,
    @SerialName("repeatNewPassword") val repeatNewPassword: String
)
