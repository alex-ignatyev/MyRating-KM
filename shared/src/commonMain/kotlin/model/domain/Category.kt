package model.domain

data class Category(
    val id: String,
    val title: String,
    val image: String,
    val subcategories: List<Category>
)
