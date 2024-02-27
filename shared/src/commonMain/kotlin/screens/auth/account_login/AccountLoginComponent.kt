package screens.auth.account_login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import data.SettingsDataSource
import domain.repository.AuthRepository
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import screens.auth.account_login.AccountLoginAction.ChangeLogin
import screens.auth.account_login.AccountLoginAction.ChangePassword
import screens.auth.account_login.AccountLoginAction.CreateAccountClick
import screens.auth.account_login.AccountLoginAction.ForgotPasswordClick
import screens.auth.account_login.AccountLoginAction.LoginClick
import screens.auth.account_login.AccountLoginAction.ShowPasswordClick
import utils.BaseComponent
import utils.EMPTY
import utils.answer.onFailure
import utils.answer.onSuccess

class DefaultAccountLoginComponent(
    componentContext: ComponentContext,
    private val openCreateAccountScreen: () -> Unit,
    private val openForgotPasswordScreen: () -> Unit,
    private val openMainScreen: () -> Unit
) : AccountLoginComponent, BaseComponent<AccountLoginEffect>(componentContext) {

    private val repository: AuthRepository by inject()

    override val state = MutableValue(AccountLoginState())

    override fun doAction(action: AccountLoginAction) {
        when (action) {
            is ChangeLogin -> changeLogin(action.value)
            is ChangePassword -> changePassword(action.value)
            is CreateAccountClick -> openCreateAccountScreen()
            is ForgotPasswordClick -> openForgotPasswordScreen()
            is ShowPasswordClick -> changePasswordVisible()
            is LoginClick -> login()
        }
    }

    private fun changeLogin(login: String) {
        state.value = state.value.copy(login = login, error = EMPTY)
    }

    private fun changePassword(password: String) {
        state.value = state.value.copy(password = password, error = EMPTY)
    }

    private fun changePasswordVisible() {
        val passwordVisible = !state.value.isPasswordHidden
        state.value = state.value.copy(isPasswordHidden = passwordVisible)
    }

    private fun login() {
        componentScope.launch {
            state.value = state.value.copy(isLoading = true, error = EMPTY)
            repository.login(
                login = state.value.login,
                password = state.value.password
            ).onSuccess {
                openMainScreen()
            }.onFailure {
                state.value = state.value.copy(isLoading = false, error = it.message)
            }
        }
    }
}

interface AccountLoginComponent {
    val state: Value<AccountLoginState>
    fun doAction(action: AccountLoginAction)
}
