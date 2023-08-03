package data

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import model.data.auth.TokenResponse
import model.data.auth.request.AccountCreateRequest
import model.data.auth.request.AccountForgotRequest
import model.data.auth.request.AccountLoginRequest
import utils.answer.Answer
import utils.answer.BaseRemoteDataSource

class RemoteAuthDataSource(
    private val httpClient: HttpClient
) : BaseRemoteDataSource() {

    suspend fun authorize(token: String): Answer<Unit> {
        return apiCall {
            httpClient.get {
                url("authorize")
                header(HttpHeaders.Authorization, token)
            }
        }
    }

    suspend fun createAccount(request: AccountCreateRequest): Answer<Unit> {
        return apiCall {
            httpClient.post {
                url("account_create")
                setBody(request)
            }
        }
    }

    suspend fun login(request: AccountLoginRequest): Answer<TokenResponse> {
        return apiCall {
            httpClient.post {
                url("account_login")
                setBody(request)
            }
        }
    }

    suspend fun forgotPassword(request: AccountForgotRequest): Answer<Unit> {
        return apiCall {
            httpClient.post {
                url("account_forgot")
                setBody(request)
            }
        }
    }
}
