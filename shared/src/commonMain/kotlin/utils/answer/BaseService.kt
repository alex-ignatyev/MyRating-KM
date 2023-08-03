package utils.answer

import com.my_rating.shared.AppRes
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import utils.answer.ErrorCode.BadRequest
import utils.answer.ErrorCode.Conflict
import utils.answer.ErrorCode.Forbidden
import utils.answer.ErrorCode.InternalError
import utils.answer.ErrorCode.MethodNotAllowed
import utils.answer.ErrorCode.Unauthorized

abstract class BaseRemoteDataSource {

    protected suspend inline fun <reified T : Any> apiCall(call: () -> HttpResponse): Answer<T> {
        val response = try {
            call.invoke()
        } catch (e: Exception) {
            return Answer.failure(code = InternalError, message = AppRes.string.error_something_went_wrong)
        }

        return when (response.status.value) {
            200 -> Answer.success(response.body())
            201 -> Answer.success(response.body())

            400 -> Answer.failure(
                code = BadRequest,
                message = response.body()
            )

            401 -> Answer.failure(
                code = Unauthorized,
                message = response.body()
            )

            403 -> Answer.failure(
                code = Forbidden,
                message = response.body()
            )

            405 -> Answer.failure(
                code = MethodNotAllowed,
                message = response.body()
            )

            409 -> Answer.failure(
                code = Conflict,
                message = response.body()
            )

            else -> Answer.failure(code = InternalError, message = AppRes.string.error_something_went_wrong)
        }
    }
}
