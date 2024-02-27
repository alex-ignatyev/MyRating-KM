package data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.url
import model.domain.Category
import model.domain.User
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import utils.answer.Answer
import utils.answer.BaseRemoteDataSource

class RemoteCategoriesDataSource(
    private val httpClient: HttpClient
) : KoinComponent, BaseRemoteDataSource() {

    val settings: SettingsDataSource by inject()

    // TODO Удалить
    suspend fun getUserInfo(login: String): Answer<User> {
        return apiCall {
            httpClient.get {
                url("auth/user_info")
                parameter("login", login)
            }
        }
    }

    suspend fun getCategories(): Answer<List<Category>> {
        //todo() Создаь запрос категорий на беке
        return Answer.success(emptyList())
    }

    suspend fun addCategory(): Answer<Unit> {
        return apiCall {
            httpClient.get {
                url("categories/add")
                //parameter("searchQuery", search)
                //header(HttpHeaders.Authorization, settings.getToken())
            }
        }
    }

    suspend fun updateCategory(): Answer<Unit> {
        return apiCall {
            httpClient.post {
                url("categories/update")
                //header(HttpHeaders.Authorization, settings.getToken())
                //parameter("userId", settings.getUserId())
                //setBody(request)
            }
        }
    }

    suspend fun deleteCategory(): Answer<Unit> {
        return apiCall {
            httpClient.post {
                url("categories/delete")
                //header(HttpHeaders.Authorization, settings.getToken())
                //parameter("userId", settings.getUserId())
                //setBody(request)
            }
        }
    }
}
