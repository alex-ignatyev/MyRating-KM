package domain.mapper

import model.data.category.response.CategoryResponse
import model.domain.Category
import utils.orZero

fun List<CategoryResponse>.toDomain(): List<Category> {
    return this.map {
        Category(
            id = it.id.orZero(),
            title = it.title.orEmpty(),
            icon = it.icon.orZero()
        )
    }
}
