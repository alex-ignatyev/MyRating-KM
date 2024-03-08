package screens.main.category.caategory_edit

sealed interface EditCategoryAction {
    data object InitEditCategoryScreen : EditCategoryAction
    data class OnCategoryTitleChange(val value: String) : EditCategoryAction
    data class OnCategoryIconChange(val value: Int) : EditCategoryAction
    data object ChangeCategory : EditCategoryAction
    data object OnBackClick : EditCategoryAction
}

data class EditCategoryState(
    val title: String = "",
    val icon: Int = 0,
    val isLoading: Boolean = false,
    val error: String = ""
)

sealed interface EditCategoryEffect
