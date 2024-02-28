package domain.mapper

import model.data.product.response.ProductResponse
import model.domain.Product
import utils.orZero

fun List<ProductResponse>.toDomain(): List<Product> {
    return this.map {
        Product(
            id = it.id.orZero(),
            title = it.title.orEmpty(),
            rate = it.rate.orZero()
        )
    }
}
