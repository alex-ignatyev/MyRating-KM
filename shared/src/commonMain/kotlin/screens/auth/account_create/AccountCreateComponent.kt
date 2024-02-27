package screens.auth.account_create

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import domain.repository.AuthRepository
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import screens.auth.account_create.AccountCreateAction.ChangeLogin
import screens.auth.account_create.AccountCreateAction.ChangeName
import screens.auth.account_create.AccountCreateAction.ChangePassword
import screens.auth.account_create.AccountCreateAction.ChangePasswordRepeat
import screens.auth.account_create.AccountCreateAction.CreateAccountClick
import screens.auth.account_create.AccountCreateAction.OnBackClick
import screens.auth.account_create.AccountCreateAction.ShowPasswordClick
import screens.auth.account_create.AccountCreateAction.ShowPasswordRepeatClick
import utils.BaseComponent
import utils.EMPTY
import utils.answer.onFailure
import utils.answer.onSuccess

class DefaultAccountCreateComponent(
    componentContext: ComponentContext,
    private val returnToPreviousScreen: () -> Unit
) : AccountCreateComponent, BaseComponent<AccountCreateEffect>(componentContext) {

    private val repository: AuthRepository by inject()

    override val state = MutableValue(AccountCreateState())

    override fun doAction(action: AccountCreateAction) {
        when (action) {
            is ChangeLogin -> changeLogin(action.value)
            is ChangeName -> changeName(action.value)
            is ChangePassword -> changePassword(action.value)
            is ShowPasswordClick -> changePasswordVisible()
            is ChangePasswordRepeat -> changeRepeatPassword(action.value)
            is ShowPasswordRepeatClick -> changeRepeatPasswordVisible()
            is CreateAccountClick -> createAccount()
            is OnBackClick -> returnToPreviousScreen()
        }
    }

    private fun changeLogin(login: String) {
        state.value = state.value.copy(login = login, error = EMPTY)
    }

    private fun changeName(name: String) {
        state.value = state.value.copy(name = name, error = EMPTY)
    }

    private fun changePassword(password: String) {
        state.value = state.value.copy(password = password, error = EMPTY)
    }

    private fun changeRepeatPassword(repeatPassword: String) {
        state.value = state.value.copy(passwordRepeat = repeatPassword, error = EMPTY)
    }

    private fun changePasswordVisible() {
        val passwordVisible = !state.value.isPasswordHidden
        state.value = state.value.copy(isPasswordHidden = passwordVisible)
    }

    private fun changeRepeatPasswordVisible() {
        val passwordVisible = !state.value.isPasswordRepeatHidden
        state.value = state.value.copy(isPasswordRepeatHidden = passwordVisible)
    }

    private fun createAccount() {
        componentScope.launch {
            state.value = state.value.copy(isLoading = true)
            repository.create(
                login = state.value.login,
                name = state.value.name,
                password = state.value.password,
                repeatPassword = state.value.passwordRepeat
            ).onSuccess {
                returnToPreviousScreen()
            }.onFailure {
                state.value = state.value.copy(isLoading = false, error = it.message)
            }
        }
    }
}

interface AccountCreateComponent {
    val state: Value<AccountCreateState>
    fun doAction(action: AccountCreateAction)
}
