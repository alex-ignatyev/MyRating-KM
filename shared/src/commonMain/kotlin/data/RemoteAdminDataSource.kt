package data

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import model.data.admin.AdminAddTobaccoRequest
import model.data.admin.CompanyResponse
import utils.answer.Answer
import utils.answer.BaseRemoteDataSource

class RemoteAdminDataSource(
    private val httpClient: HttpClient
) : BaseRemoteDataSource() {

    suspend fun addTobacco(token: String, request: AdminAddTobaccoRequest): Answer<Unit> {
        return apiCall {
            httpClient.post {
                url("admin_add_tobacco")
                header(HttpHeaders.Authorization, token)
                setBody(request)
            }
        }
    }

    suspend fun getCompanies(token: String): Answer<List<CompanyResponse>> {
        return apiCall {
            httpClient.get {
                url("admin_tobacco_companies")
                header(HttpHeaders.Authorization, token)
            }
        }
    }
}
