package model.domain

data class Category(
    override val id: String,
    override val title: String,
    override val image: String,
    val subcategories: List<Subcategory>
) : CategoryParent {

    data class Subcategory(
        override val id: String,
        override val title: String,
        override val image: String
    ) : CategoryParent
}

interface CategoryParent {
    val id: String
    val title: String
    val image: String
}
