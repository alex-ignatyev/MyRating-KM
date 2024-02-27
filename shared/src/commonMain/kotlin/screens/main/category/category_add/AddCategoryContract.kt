package screens.main.category.category_add

sealed interface AddCategoryAction {
    data class OnCategoryChange(val value: String) : AddCategoryAction
    data object AddCategory : AddCategoryAction
    data object OnBackClick : AddCategoryAction
}

data class AddCategoryState(
    val title: String = "",
    val isLoading: Boolean = false,
    val isButtonEnabled: Boolean = false,
    val error: String = ""
)

sealed interface AddCategoryEffect
