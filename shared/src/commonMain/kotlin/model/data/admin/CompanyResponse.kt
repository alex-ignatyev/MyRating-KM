package model.data.admin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyResponse(
    @SerialName("id") val id: String?,
    @SerialName("company") val company: String?,
    @SerialName("lines") val lines: List<String>?
)
