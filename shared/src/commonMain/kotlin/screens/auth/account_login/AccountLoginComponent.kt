package screens.auth.account_login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import data.remote.SettingsDataSource
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
import utils.LOGIN_MIN_LENGTH
import utils.PASSWORD_MIN_LENGTH
import utils.SPACE
import utils.answer.onFailure
import utils.answer.onSuccess

class DefaultAccountLoginComponent(
    componentContext: ComponentContext,
    private val openCreateAccountScreen: () -> Unit,
    private val openForgotPasswordScreen: () -> Unit,
    private val openMainScreen: () -> Unit
) : AccountLoginComponent, BaseComponent<AccountLoginEffect>(componentContext) {

    private val repository: AuthRepository by inject()
    private val settings: SettingsDataSource by inject()

    override val state = MutableValue(AccountLoginState())

    override fun doAction(action: AccountLoginAction) {
        when (action) {
            is ChangeLogin -> changeLogin(action.value)
            is ChangePassword -> changePassword(action.value)
            is CreateAccountClick -> openCreateAccountScreen.invoke()
            is ForgotPasswordClick -> openForgotPasswordScreen.invoke()
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
        if (isFieldsNotCorrect()) return
        componentScope.launch {
            state.value = state.value.copy(isLoading = true, error = EMPTY)
            repository.login(
                login = state.value.login.trim(),
                password = state.value.password.trim()
            ).onSuccess { login ->
                settings.saveInfo(login = login)
                openMainScreen.invoke()
            }.onFailure {
                state.value = state.value.copy(isLoading = false, error = it.message)
            }
        }
    }

    private fun isFieldsNotCorrect(): Boolean {
        if (
            state.value.login.contains(SPACE) ||
            state.value.password.contains(SPACE)
        ) {
            state.value = state.value.copy(error = "Can't use spaces")
            return true
        }

        if (state.value.login.length < LOGIN_MIN_LENGTH) {
            state.value = state.value.copy(error = "Login should be more than 3 symbols")
            return true
        }

        if (state.value.password.length < PASSWORD_MIN_LENGTH) {
            state.value = state.value.copy(error = "Password should be more than 3 symbols")
            return true
        }

        return false
    }
}

interface AccountLoginComponent {
    val state: Value<AccountLoginState>
    fun doAction(action: AccountLoginAction)
}
