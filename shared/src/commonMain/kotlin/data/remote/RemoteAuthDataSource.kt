package data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import model.data.auth.request.ForgotRequest
import model.data.auth.request.LoginRequest
import model.data.auth.request.PasswordRequest
import model.data.auth.request.RegisterRequest
import model.data.auth.response.LoginResponse
import model.data.auth.response.UserResponse
import utils.answer.Answer
import utils.answer.BaseRemoteDataSource

class RemoteAuthDataSource(
    private val httpClient: HttpClient
) : BaseRemoteDataSource() {

    suspend fun getUserInfo(login: String): Answer<UserResponse> {
        return apiCall {
            httpClient.get {
                url("auth/user_info")
                parameter("login", login)
            }
        }
    }

    suspend fun register(request: RegisterRequest): Answer<Unit> {
        return apiCall {
            httpClient.post {
                url("auth/register")
                setBody(request)
            }
        }
    }

    suspend fun login(request: LoginRequest): Answer<LoginResponse> {
        return apiCall {
            httpClient.post {
                url("auth/login")
                setBody(request)
            }
        }
    }

    suspend fun forgot(request: ForgotRequest): Answer<Unit> {
        return apiCall {
            httpClient.post {
                url("auth/forgot")
                setBody(request)
            }
        }
    }

    suspend fun changePassword(login: String, request: PasswordRequest): Answer<Unit> {
        return apiCall {
            httpClient.post {
                url("auth/change_password")
                parameter("login", login)
                setBody(request)
            }
        }
    }
}
