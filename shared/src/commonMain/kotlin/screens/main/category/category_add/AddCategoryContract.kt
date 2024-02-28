package screens.main.category.category_add

sealed interface AddCategoryAction {
    data class OnCategoryChange(val value: String) : AddCategoryAction
    data class OnCategoryIconChange(val value: Int) : AddCategoryAction
    data object AddCategory : AddCategoryAction
    data object OnBackClick : AddCategoryAction
}

data class AddCategoryState(
    val title: String = "",
    val icon: Int = 0,
    val isLoading: Boolean = false,
    val error: String = ""
)

sealed interface AddCategoryEffect
