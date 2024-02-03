package screens.auth.account_forgot

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackCallback
import domain.repository.AuthRepository
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import screens.auth.account_forgot.AccountForgotAction.ChangeLogin
import screens.auth.account_forgot.AccountForgotAction.ChangePassword
import screens.auth.account_forgot.AccountForgotAction.ChangePasswordRepeat
import screens.auth.account_forgot.AccountForgotAction.OnBackClick
import screens.auth.account_forgot.AccountForgotAction.ResetPasswordClick
import screens.auth.account_forgot.AccountForgotAction.ShowPasswordClick
import screens.auth.account_forgot.AccountForgotAction.ShowPasswordRepeatClick
import utils.BaseComponent
import utils.EMPTY
import utils.answer.onFailure
import utils.answer.onSuccess

class DefaultAccountForgotComponent(
    componentContext: ComponentContext,
    private val returnToPreviousScreen: () -> Unit
) : AccountForgotComponent, BaseComponent<AccountForgotEffect>(componentContext) {

    private val repository: AuthRepository by inject()

    override val state = MutableValue(AccountForgotState())

    override fun doAction(action: AccountForgotAction) {
        when (action) {
            is ChangeLogin -> changeLogin(action.value)
            is ChangePassword -> changeNewPassword(action.value)
            is ShowPasswordClick -> changeNewPasswordVisibility()
            is ChangePasswordRepeat -> changeRepeatNewPassword(action.value)
            is ShowPasswordRepeatClick -> changeRepeatNewPasswordVisibility()
            is ResetPasswordClick -> resetPassword()
            is OnBackClick -> returnToPreviousScreen()
        }
    }

    private fun changeLogin(login: String) {
        state.value = state.value.copy(login = login, error = EMPTY)
    }

    private fun changeNewPassword(newPassword: String) {
        state.value = state.value.copy(password = newPassword, error = EMPTY)
    }

    private fun changeRepeatNewPassword(repeatNewPassword: String) {
        state.value = state.value.copy(passwordRepeat = repeatNewPassword, error = EMPTY)
    }

    private fun changeNewPasswordVisibility() {
        val passwordVisible = !state.value.isPasswordHidden
        state.value = state.value.copy(isPasswordHidden = passwordVisible)
    }

    private fun changeRepeatNewPasswordVisibility() {
        val passwordVisible = !state.value.isPasswordRepeatHidden
        state.value = state.value.copy(isPasswordRepeatHidden = passwordVisible)
    }

    private fun resetPassword() {
        componentScope.launch {
            state.value = state.value.copy(isLoading = true)
            repository.forgot(
                login = state.value.login,
                newPassword = state.value.password,
                repeatNewPassword = state.value.passwordRepeat
            ).onSuccess {
                returnToPreviousScreen()
            }.onFailure {
                state.value = state.value.copy(isLoading = false, error = it.message)
            }
        }
    }
}

interface AccountForgotComponent {
    val state: Value<AccountForgotState>
    fun doAction(action: AccountForgotAction)
}
