package screens.main.category.category_feed

import model.domain.Category

sealed interface CategoryFeedAction {
    data object InitCategoryFeedScreen : CategoryFeedAction
    data object AddCategory : CategoryFeedAction
    data object EditCategory : CategoryFeedAction
    data class DeleteCategory(val categoryId: Long) : CategoryFeedAction
    data object OnDataRefresh : CategoryFeedAction
    data object OnErrorRefresh : CategoryFeedAction
    data class OnCategoryClick(val category: Category) : CategoryFeedAction
    data class OnEditCategoryClick(val category: Category) : CategoryFeedAction
}

data class CategoryFeedState(
    val data: List<Category> = emptyList(),
    val isLoading: Boolean = true,
    val isEdit: Boolean = false,
    val error: String = ""
)

sealed class CategoryFeedEffect
