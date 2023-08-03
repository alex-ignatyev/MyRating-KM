package data

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import model.data.tobacco.TobaccoFeedResponse
import model.data.tobacco.TobaccoInfoRequest
import model.data.tobacco.TobaccoInfoResponse
import model.data.tobacco.TobaccoVoteRequest
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import utils.answer.Answer
import utils.answer.BaseRemoteDataSource

class RemoteMainDataSource(
    private val httpClient: HttpClient
) : KoinComponent, BaseRemoteDataSource() {

    val settings: SettingsDataSource by inject()

    suspend fun getTobaccoFeed(search: String): Answer<List<TobaccoFeedResponse>> {
        return apiCall {
            httpClient.get {
                url("tobaccos")
                parameter("searchQuery", search)
                header(HttpHeaders.Authorization, settings.getToken())
            }
        }
    }

    suspend fun postTobaccoInfo(request: TobaccoInfoRequest): Answer<TobaccoInfoResponse> {
        return apiCall {
            httpClient.post {
                url("tobacco_info")
                header(HttpHeaders.Authorization, settings.getToken())
                parameter("userId", settings.getUserId())
                setBody(request)
            }
        }
    }

    suspend fun postTobaccoVote(request: TobaccoVoteRequest): Answer<Unit> {
        return apiCall {
            httpClient.post {
                url("vote_tobacco")
                header(HttpHeaders.Authorization, settings.getToken())
                parameter("userId", settings.getUserId())
                setBody(request)
            }
        }
    }
}
