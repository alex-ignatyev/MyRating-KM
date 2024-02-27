package data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import model.data.auth.request.AccountCreateRequest
import model.data.auth.request.AccountForgotRequest
import model.data.auth.request.AccountLoginRequest
import model.domain.User
import utils.answer.Answer
import utils.answer.BaseRemoteDataSource

class RemoteAuthDataSource(
    private val httpClient: HttpClient
) : BaseRemoteDataSource() {

    suspend fun getUserInfo(login: String): Answer<User> {
        return apiCall {
            httpClient.get {
                url("auth/user_info")
                parameter("login", login)
            }
        }
    }

    suspend fun register(request: AccountCreateRequest): Answer<Unit> {
        return apiCall {
            httpClient.post {
                url("auth/register")
                setBody(request)
            }
        }
    }

    suspend fun login(request: AccountLoginRequest): Answer<Unit> {
        return apiCall {
            httpClient.post {
                url("auth/login")
                setBody(request)
            }
        }
    }

    suspend fun forgotPassword(request: AccountForgotRequest): Answer<Unit> {
        return apiCall {
            httpClient.post {
                url("auth/forgot")
                setBody(request)
            }
        }
    }
}
