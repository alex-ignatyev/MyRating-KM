package utils.answer

import com.my_rating.shared.AppRes
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

abstract class BaseRemoteDataSource {

    protected suspend inline fun <reified T : Any> apiCall(call: () -> HttpResponse): Answer<T> {
        val httpResponse = try {
            call.invoke()
        } catch (e: Exception) {
            println(e)
            return Answer.failure(code = 500, message = AppRes.string.error_something_went_wrong)
        }

        val code = httpResponse.status.value
        return if (code == 200) {
            Answer.success(httpResponse.body())
        } else {
            try {
                val response = Json.decodeFromString<ApiError>(httpResponse.body())
                Answer.failure(code = code, message = response.message)
            } catch (e: Exception) {
                Answer.failure(code = code, message = e.message.toString())
            }
        }
    }
}

@Serializable
data class ApiError(
    @SerialName("statusCode") val statusCode: Int,
    @SerialName("message") val message: String,
    @SerialName("description") val description: String
)
