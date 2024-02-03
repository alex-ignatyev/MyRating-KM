package screens.main.category.category_add

import model.domain.Company

sealed interface AddCategoryAction {
    data object InitAddCategoryScreen : AddCategoryAction
    data class ChangeManual(val isChecked: Boolean) : AddCategoryAction
    data class ChangeCompany(val value: String) : AddCategoryAction
    data class ChangeTaste(val value: String) : AddCategoryAction
    data class ChangeLine(val value: String) : AddCategoryAction
    data class ChangeStrengthByCompany(val value: String) : AddCategoryAction
    data object OnCompanyClick : AddCategoryAction
    class OnLineClick(val lines: List<String>) : AddCategoryAction
    data object AddCategoryClick : AddCategoryAction
    data object OnBackClick : AddCategoryAction
}

data class AddCategoryState(
    val isLoading: Boolean = true,
    val isManual: Boolean = false,
    val tobacco: Tobacco = Tobacco(),
    val companies: List<Company> = emptyList(),
    val isButtonEnabled: Boolean = false,
    val error: String = ""
)

sealed interface AddCategoryEffect {
    data object ReturnToPreviousScreen : AddCategoryEffect
    data object OpenCompanySheet : AddCategoryEffect
    data class OpenLineSheet(val lines: List<String>) : AddCategoryEffect
}

data class Tobacco(
    val company: String = "",
    val taste: String = "",
    val line: String = "",
    val strength: String = "",
)
