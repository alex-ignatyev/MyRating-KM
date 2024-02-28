package data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import model.data.product.request.AddProductRequest
import model.data.product.response.ProductResponse
import utils.answer.Answer
import utils.answer.BaseRemoteDataSource

class RemoteProductsDataSource(
    private val httpClient: HttpClient
) : BaseRemoteDataSource() {

    suspend fun getProducts(categoryId: Long): Answer<List<ProductResponse>> {
        return apiCall {
            httpClient.get {
                url("products/all")
                parameter("categoryId", categoryId)
            }
        }
    }

    suspend fun addProduct(login: String, request: AddProductRequest): Answer<Unit> {
        return apiCall {
            httpClient.post {
                url("products/add")
                parameter("login", login)
                setBody(request)
            }
        }
    }
}
