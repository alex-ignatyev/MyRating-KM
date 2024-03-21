package screens.auth.account_forgot

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.my_rating.shared.strings.AppResStrings
import domain.repository.AuthRepository
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import screens.auth.account_forgot.AccountForgotAction.ChangeEmail
import screens.auth.account_forgot.AccountForgotAction.ChangePassword
import screens.auth.account_forgot.AccountForgotAction.ChangePasswordRepeat
import screens.auth.account_forgot.AccountForgotAction.OnBackClick
import screens.auth.account_forgot.AccountForgotAction.ResetPasswordClick
import screens.auth.account_forgot.AccountForgotAction.ShowPasswordClick
import screens.auth.account_forgot.AccountForgotAction.ShowPasswordRepeatClick
import utils.BaseComponent
import utils.EMAIL_MIN_LENGTH
import utils.EMPTY
import utils.PASSWORD_MIN_LENGTH
import utils.SPACE
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
            is ChangeEmail -> changeEmail(action.value)
            is ChangePassword -> changeNewPassword(action.value)
            is ShowPasswordClick -> changeNewPasswordVisibility()
            is ChangePasswordRepeat -> changeRepeatNewPassword(action.value)
            is ShowPasswordRepeatClick -> changeRepeatNewPasswordVisibility()
            is ResetPasswordClick -> resetPassword()
            is OnBackClick -> returnToPreviousScreen.invoke()
        }
    }

    private fun changeEmail(email: String) {
        state.value = state.value.copy(email = email, error = EMPTY)
    }

    private fun changeNewPassword(newPassword: String) {
        state.value = state.value.copy(password = newPassword, error = EMPTY)
    }

    private fun changeNewPasswordVisibility() {
        state.value = state.value.copy(isPasswordHidden = !state.value.isPasswordHidden)
    }

    private fun changeRepeatNewPassword(repeatNewPassword: String) {
        state.value = state.value.copy(passwordRepeat = repeatNewPassword, error = EMPTY)
    }

    private fun changeRepeatNewPasswordVisibility() {
        state.value = state.value.copy(isPasswordRepeatHidden = !state.value.isPasswordRepeatHidden)
    }

    private fun resetPassword() {
        if (isFieldsNotCorrect()) return
        componentScope.launch {
            state.value = state.value.copy(isLoading = true)
            repository.forgot(
                email = state.value.email.trim(),
                newPassword = state.value.password.trim(),
                repeatNewPassword = state.value.passwordRepeat.trim()
            ).onSuccess {
                returnToPreviousScreen.invoke()
            }.onFailure {
                state.value = state.value.copy(isLoading = false, error = it.message)
            }
        }
    }

    private fun isFieldsNotCorrect(): Boolean {
        if (
            state.value.email.contains(SPACE) ||
            state.value.password.contains(SPACE) ||
            state.value.passwordRepeat.contains(SPACE)
        ) {
            state.value = state.value.copy(error = AppResStrings.error_spaces)
            return true
        }

        if (state.value.email.length < EMAIL_MIN_LENGTH) {
            state.value = state.value.copy(error = AppResStrings.error_email_length.format(EMAIL_MIN_LENGTH.toString()))
        }

        if (state.value.password.length < PASSWORD_MIN_LENGTH) {
            state.value = state.value.copy(error = AppResStrings.error_password_length.format(PASSWORD_MIN_LENGTH.toString()))
            return true
        }

        if (state.value.password != state.value.passwordRepeat) {
            state.value = state.value.copy(error = AppResStrings.error_passwords_match)
        }

        return false
    }
}

interface AccountForgotComponent {
    val state: Value<AccountForgotState>
    fun doAction(action: AccountForgotAction)
}
