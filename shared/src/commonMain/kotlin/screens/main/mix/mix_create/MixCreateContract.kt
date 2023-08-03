package screens.main.mix.mix_create

import model.domain.Company

sealed class MixCreateEvent {
    class InitMixCreateScreen : MixCreateEvent()
    data class ChangeManual(val isChecked: Boolean) : MixCreateEvent()
    data class ChangeCompany(val value: String) : MixCreateEvent()
    data class ChangeTaste(val value: String) : MixCreateEvent()
    data class ChangeLine(val value: String) : MixCreateEvent()
    data class ChangeStrengthByCompany(val value: String) : MixCreateEvent()
    class OnCompanyClick : MixCreateEvent()
    class OnLineClick(val lines: List<String>) : MixCreateEvent()
    class AddTobaccoClick : MixCreateEvent()
    class OnBackClick : MixCreateEvent()
    class ClearActions : MixCreateEvent()
}

//TODO Создать модель для всех полей
data class MixCreateState(
    val isLoading: Boolean = true,
    val isManual: Boolean = false,
    val company: String = "",
    val taste: String = "",
    val line: String = "",
    val strength: String = "",
    val companies: List<Company> = emptyList(),
    val isButtonEnabled: Boolean = false,
    val error: String = ""
)

sealed class MixCreateAction {
    class ReturnToPreviousScreen : MixCreateAction()
    class OpenCompanySheet : MixCreateAction()
    data class OpenLineSheet(val lines: List<String>) : MixCreateAction()
}
