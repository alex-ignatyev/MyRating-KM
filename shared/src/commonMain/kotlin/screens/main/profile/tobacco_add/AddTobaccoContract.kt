package screens.main.profile.tobacco_add

import model.domain.Company

sealed class AddTobaccoEvent {
    class InitAddTobaccoScreen : AddTobaccoEvent()
    data class ChangeManual(val isChecked: Boolean) : AddTobaccoEvent()
    data class ChangeCompany(val value: String) : AddTobaccoEvent()
    data class ChangeTaste(val value: String) : AddTobaccoEvent()
    data class ChangeLine(val value: String) : AddTobaccoEvent()
    data class ChangeStrengthByCompany(val value: String) : AddTobaccoEvent()
    class OnCompanyClick : AddTobaccoEvent()
    class OnLineClick(val lines: List<String>) : AddTobaccoEvent()
    class AddTobaccoClick : AddTobaccoEvent()
    class OnBackClick : AddTobaccoEvent()
    class ClearActions : AddTobaccoEvent()
}

//TODO Создать модель для всех полей
data class AddTobaccoState(
    val isLoading: Boolean = true,
    val isManual: Boolean = false,
    val tobacco: Tobacco = Tobacco(),
    val companies: List<Company> = emptyList(),
    val isButtonEnabled: Boolean = false,
    val error: String = ""
)

sealed class AddTobaccoAction {
    class ReturnToPreviousScreen : AddTobaccoAction()
    class OpenCompanySheet : AddTobaccoAction()
    data class OpenLineSheet(val lines: List<String>) : AddTobaccoAction()
}

data class Tobacco(
    val company: String = "",
    val taste: String = "",
    val line: String = "",
    val strength: String = "",
)
