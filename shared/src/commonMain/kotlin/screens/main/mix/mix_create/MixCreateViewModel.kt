package screens.main.mix.mix_create

import com.adeo.kviewmodel.BaseSharedViewModel
import domain.repository.AdminRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import screens.main.mix.mix_create.MixCreateAction.OpenCompanySheet
import screens.main.mix.mix_create.MixCreateAction.OpenLineSheet
import screens.main.mix.mix_create.MixCreateAction.ReturnToPreviousScreen
import screens.main.mix.mix_create.MixCreateEvent.AddTobaccoClick
import screens.main.mix.mix_create.MixCreateEvent.ChangeCompany
import screens.main.mix.mix_create.MixCreateEvent.ChangeLine
import screens.main.mix.mix_create.MixCreateEvent.ChangeManual
import screens.main.mix.mix_create.MixCreateEvent.ChangeStrengthByCompany
import screens.main.mix.mix_create.MixCreateEvent.ChangeTaste
import screens.main.mix.mix_create.MixCreateEvent.ClearActions
import screens.main.mix.mix_create.MixCreateEvent.InitMixCreateScreen
import screens.main.mix.mix_create.MixCreateEvent.OnBackClick
import screens.main.mix.mix_create.MixCreateEvent.OnCompanyClick
import screens.main.mix.mix_create.MixCreateEvent.OnLineClick
import utils.EMPTY
import utils.answer.onFailure
import utils.answer.onSuccess
import utils.areNotEmpty
import utils.toLongOrDef

class MixCreateViewModel : KoinComponent,
    BaseSharedViewModel<MixCreateState, MixCreateAction, MixCreateEvent>(
        initialState = MixCreateState()
    ) {

    private val repo: AdminRepository by inject()

    override fun obtainEvent(viewEvent: MixCreateEvent) {
        when (viewEvent) {
            is InitMixCreateScreen -> fetchData()
            is ChangeManual -> changeInputType(viewEvent.isChecked)
            is ChangeCompany -> changeCompany(viewEvent.value)
            is ChangeTaste -> changeTaste(viewEvent.value)
            is ChangeLine -> changeLine(viewEvent.value)
            is ChangeStrengthByCompany -> changeStrengthByCompany(viewEvent.value)
            is AddTobaccoClick -> addTobacco()
            is OnBackClick -> returnToPreviousScreen()
            is OnCompanyClick -> openCompaniesSheet()
            is OnLineClick -> openLinesSheet(viewEvent.lines)
            is ClearActions -> clearActions()
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            repo.getCompanies().onSuccess {
                viewState = viewState.copy(isLoading = false, companies = it)
            }.onFailure {
                viewState = viewState.copy(isLoading = false, error = it.message)
            }
        }
    }

    private fun changeInputType(isChecked: Boolean) {
        viewState = viewState.copy(isManual = isChecked)
    }

    private fun changeCompany(company: String) {
        viewState = viewState.copy(company = company, line = EMPTY, error = EMPTY, isButtonEnabled = isButtonEnabled())
    }

    private fun changeTaste(taste: String) {
        viewState = viewState.copy(taste = taste, error = EMPTY, isButtonEnabled = isButtonEnabled())
    }

    private fun changeLine(line: String) {
        viewState = viewState.copy(line = line, error = EMPTY, isButtonEnabled = isButtonEnabled())
    }

    private fun changeStrengthByCompany(strength: String) {
        viewState = viewState.copy(strength = strength, error = EMPTY, isButtonEnabled = isButtonEnabled())
    }

    private fun openCompaniesSheet() {
        viewAction = OpenCompanySheet()
    }

    private fun openLinesSheet(lines: List<String>) {
        viewAction = OpenLineSheet(lines)
    }

    private fun addTobacco() {
        viewModelScope.launch {
            viewState = viewState.copy(isLoading = true)
            repo.addTobacco(
                taste = viewState.taste,
                company = viewState.company,
                line = viewState.line,
                strength = viewState.strength.toLongOrDef()
            ).onSuccess {
                viewAction = ReturnToPreviousScreen()
            }.onFailure {
                viewState = viewState.copy(isLoading = false, error = it.message)
            }
        }
    }

    private fun isButtonEnabled(): Boolean {
        return areNotEmpty(viewState.company, viewState.line) && viewState.strength.toLongOrDef() in 0L..10L
    }

    private fun returnToPreviousScreen() {
        viewAction = ReturnToPreviousScreen()
    }

    private fun clearActions() {
        viewAction = null
        viewState = viewState.copy(isLoading = false, error = EMPTY)
    }
}
