package screens.main.category.category_add

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import domain.repository.AdminRepository
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import screens.main.category.category_add.AddCategoryAction.AddCategoryClick
import screens.main.category.category_add.AddCategoryAction.ChangeCompany
import screens.main.category.category_add.AddCategoryAction.ChangeLine
import screens.main.category.category_add.AddCategoryAction.ChangeManual
import screens.main.category.category_add.AddCategoryAction.ChangeStrengthByCompany
import screens.main.category.category_add.AddCategoryAction.ChangeTaste
import screens.main.category.category_add.AddCategoryAction.InitAddCategoryScreen
import screens.main.category.category_add.AddCategoryAction.OnBackClick
import screens.main.category.category_add.AddCategoryAction.OnCompanyClick
import screens.main.category.category_add.AddCategoryAction.OnLineClick
import utils.BaseComponent
import utils.EMPTY
import utils.answer.onFailure
import utils.answer.onSuccess
import utils.areNotEmpty
import utils.toLongOrDef

class DefaultAddCategoryComponent(
    componentContext: ComponentContext,
    private val returnToPreviousScreen: () -> Unit
) : AddCategoryComponent, BaseComponent<AddCategoryEffect>(componentContext) {

    private val repo: AdminRepository by inject()

    override val state = MutableValue(AddCategoryState())

    override fun doAction(action: AddCategoryAction) {
        when (action) {
            is InitAddCategoryScreen -> fetchData()
            is ChangeManual -> changeInputType(action.isChecked)
            is ChangeCompany -> changeCompany(action.value)
            is ChangeTaste -> changeTaste(action.value)
            is ChangeLine -> changeLine(action.value)
            is ChangeStrengthByCompany -> changeStrengthByCompany(action.value)
            is AddCategoryClick -> addTobacco()
            is OnBackClick -> returnToPreviousScreen.invoke()
            is OnCompanyClick -> openCompaniesSheet()
            is OnLineClick -> openLinesSheet(action.lines)
        }
    }

    private fun fetchData() {
        componentScope.launch {
            repo.getCompanies().onSuccess {
                state.value = state.value.copy(isLoading = false, companies = it)
            }.onFailure {
                state.value = state.value.copy(isLoading = false, error = it.message)
            }
        }
    }

    private fun changeInputType(isChecked: Boolean) {
        state.value = state.value.copy(isManual = isChecked)
    }

    private fun changeCompany(company: String) {
        state.value = state.value.copy(
            tobacco = state.value.tobacco.copy(company = company, line = EMPTY),
            error = EMPTY,
            isButtonEnabled = isButtonEnabled()
        )
    }

    private fun changeTaste(taste: String) {
        state.value =
            state.value.copy(tobacco = state.value.tobacco.copy(taste = taste), error = EMPTY, isButtonEnabled = isButtonEnabled())
    }

    private fun changeLine(line: String) {
        state.value = state.value.copy(tobacco = state.value.tobacco.copy(line = line), error = EMPTY, isButtonEnabled = isButtonEnabled())
    }

    private fun changeStrengthByCompany(strength: String) {
        state.value =
            state.value.copy(tobacco = state.value.tobacco.copy(strength = strength), error = EMPTY, isButtonEnabled = isButtonEnabled())
    }

    private fun openCompaniesSheet() {
        // viewAction = OpenCompanySheet()
    }

    private fun openLinesSheet(lines: List<String>) {
        // viewAction = OpenLineSheet(lines)
    }

    private fun addTobacco() {
        componentScope.launch {
            state.value = state.value.copy(isLoading = true)
            repo.addTobacco(
                taste = state.value.tobacco.taste,
                company = state.value.tobacco.company,
                line = state.value.tobacco.line,
                strength = state.value.tobacco.strength.toLongOrDef()
            ).onSuccess {
                returnToPreviousScreen.invoke()
            }.onFailure {
                state.value = state.value.copy(isLoading = false, error = it.message)
            }
        }
    }

    private fun isButtonEnabled(): Boolean {
        return areNotEmpty(state.value.tobacco.company, state.value.tobacco.line) && state.value.tobacco.strength.toLongOrDef() in 0L..10L
    }
}


interface AddCategoryComponent {
    val state: Value<AddCategoryState>
    fun doAction(action: AddCategoryAction)
}
