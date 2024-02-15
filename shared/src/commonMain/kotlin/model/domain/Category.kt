package model.domain

import kotlin.random.Random

data class Category(
    val id: String = Random.nextInt(10000, 50000).toString(),
    val title: String = "",
    val image: String = "",
    val subcategories: List<Category> = emptyList()
)
