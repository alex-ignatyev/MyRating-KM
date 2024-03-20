package model.data.auth.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("id") val id: Long?,
    @SerialName("login") val login: String?,
    @SerialName("email") val email: String?,
    @SerialName("phone") val phone: String?
)
