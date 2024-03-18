package domain.repository

import data.remote.RemoteProductsDataSource
import data.remote.SettingsDataSource
import domain.mapper.toDomain
import model.data.product.request.AddProductRequest
import model.data.product.request.UpdateProductRequest
import model.domain.Product
import utils.answer.Answer
import utils.answer.map

class ProductRepositoryImpl(
    private val remote: RemoteProductsDataSource,
    private val settings: SettingsDataSource
) : ProductRepository {

    override suspend fun getProducts(categoryId: Long): Answer<List<Product>> {
        return remote.getProducts(categoryId = categoryId).map { it.toDomain() }
    }

    override suspend fun addProduct(request: AddProductRequest): Answer<Unit> {
        return remote.addProduct(login = settings.getUserLogin(), request = request)
    }

    override suspend fun updateProduct(request: UpdateProductRequest): Answer<Unit> {
        return remote.updateProduct(login = settings.getUserLogin(), request = request)
    }

    override suspend fun deleteProduct(categoryId: Long): Answer<Unit> {
        TODO("Not yet implemented")
    }
}

interface ProductRepository {
    suspend fun getProducts(categoryId: Long): Answer<List<Product>>
    suspend fun addProduct(request: AddProductRequest): Answer<Unit>
    suspend fun updateProduct(request: UpdateProductRequest): Answer<Unit>
    suspend fun deleteProduct(categoryId: Long): Answer<Unit>
}
