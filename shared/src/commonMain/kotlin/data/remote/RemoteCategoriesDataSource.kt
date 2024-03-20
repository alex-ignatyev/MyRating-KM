package data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import model.data.category.request.AddCategoryRequest
import model.data.category.response.CategoryResponse
import utils.answer.Answer
import utils.answer.BaseRemoteDataSource

class RemoteCategoriesDataSource(
    private val httpClient: HttpClient
) : BaseRemoteDataSource() {

    suspend fun getCategories(login: String): Answer<List<CategoryResponse>> {
        return apiCall {
            httpClient.get {
                url("categories/all")
                parameter("login", login)
            }
        }
    }

    suspend fun addCategory(login: String, request: AddCategoryRequest): Answer<Unit> {
        return apiCall {
            httpClient.post {
                url("categories/add")
                parameter("login", login)
                setBody(request)
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
