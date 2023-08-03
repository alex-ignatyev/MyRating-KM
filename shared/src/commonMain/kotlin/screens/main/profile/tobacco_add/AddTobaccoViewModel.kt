package screens.main.profile.tobacco_add

import com.adeo.kviewmodel.BaseSharedViewModel
import domain.repository.AdminRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import screens.main.profile.tobacco_add.AddTobaccoAction.OpenCompanySheet
import screens.main.profile.tobacco_add.AddTobaccoAction.OpenLineSheet
import screens.main.profile.tobacco_add.AddTobaccoAction.ReturnToPreviousScreen
import screens.main.profile.tobacco_add.AddTobaccoEvent.AddTobaccoClick
import screens.main.profile.tobacco_add.AddTobaccoEvent.ChangeCompany
import screens.main.profile.tobacco_add.AddTobaccoEvent.ChangeLine
import screens.main.profile.tobacco_add.AddTobaccoEvent.ChangeManual
import screens.main.profile.tobacco_add.AddTobaccoEvent.ChangeStrengthByCompany
import screens.main.profile.tobacco_add.AddTobaccoEvent.ChangeTaste
import screens.main.profile.tobacco_add.AddTobaccoEvent.ClearActions
import screens.main.profile.tobacco_add.AddTobaccoEvent.InitAddTobaccoScreen
import screens.main.profile.tobacco_add.AddTobaccoEvent.OnBackClick
import screens.main.profile.tobacco_add.AddTobaccoEvent.OnCompanyClick
import screens.main.profile.tobacco_add.AddTobaccoEvent.OnLineClick
import utils.EMPTY
import utils.answer.onFailure
import utils.answer.onSuccess
import utils.areNotEmpty
import utils.toLongOrDef

class AddTobaccoViewModel : KoinComponent,
    BaseSharedViewModel<AddTobaccoState, AddTobaccoAction, AddTobaccoEvent>(
        initialState = AddTobaccoState()
    ) {

    private val repo: AdminRepository by inject()

    override fun obtainEvent(viewEvent: AddTobaccoEvent) {
        when (viewEvent) {
            is InitAddTobaccoScreen -> fetchData()
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
        viewState = viewState.copy(
            tobacco = viewState.tobacco.copy(company = company, line = EMPTY),
            error = EMPTY,
            isButtonEnabled = isButtonEnabled()
        )
    }

    private fun changeTaste(taste: String) {
        viewState = viewState.copy(tobacco = viewState.tobacco.copy(taste = taste), error = EMPTY, isButtonEnabled = isButtonEnabled())
    }

    private fun changeLine(line: String) {
        viewState = viewState.copy(tobacco = viewState.tobacco.copy(line = line), error = EMPTY, isButtonEnabled = isButtonEnabled())
    }

    private fun changeStrengthByCompany(strength: String) {
        viewState =
            viewState.copy(tobacco = viewState.tobacco.copy(strength = strength), error = EMPTY, isButtonEnabled = isButtonEnabled())
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
                taste = viewState.tobacco.taste,
                company = viewState.tobacco.company,
                line = viewState.tobacco.line,
                strength = viewState.tobacco.strength.toLongOrDef()
            ).onSuccess {
                viewAction = ReturnToPreviousScreen()
            }.onFailure {
                viewState = viewState.copy(isLoading = false, error = it.message)
            }
        }
    }

    private fun isButtonEnabled(): Boolean {
        return areNotEmpty(viewState.tobacco.company, viewState.tobacco.line) && viewState.tobacco.strength.toLongOrDef() in 0L..10L
    }

    private fun returnToPreviousScreen() {
        viewAction = ReturnToPreviousScreen()
    }

    private fun clearActions() {
        viewAction = null
        viewState = viewState.copy(isLoading = false, error = EMPTY)
    }
}
