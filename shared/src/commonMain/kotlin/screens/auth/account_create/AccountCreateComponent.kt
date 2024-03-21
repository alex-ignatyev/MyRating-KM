package screens.auth.account_create

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.my_rating.shared.strings.AppResStrings
import domain.repository.AuthRepository
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import screens.auth.account_create.AccountCreateAction.ChangeEmail
import screens.auth.account_create.AccountCreateAction.ChangeLogin
import screens.auth.account_create.AccountCreateAction.ChangePassword
import screens.auth.account_create.AccountCreateAction.ChangePasswordRepeat
import screens.auth.account_create.AccountCreateAction.ChangePhone
import screens.auth.account_create.AccountCreateAction.CreateAccountClick
import screens.auth.account_create.AccountCreateAction.OnBackClick
import screens.auth.account_create.AccountCreateAction.ShowPasswordClick
import screens.auth.account_create.AccountCreateAction.ShowPasswordRepeatClick
import utils.BaseComponent
import utils.EMAIL_MIN_LENGTH
import utils.EMPTY
import utils.LOGIN_MIN_LENGTH
import utils.PASSWORD_MIN_LENGTH
import utils.PHONE_MIN_LENGTH
import utils.SPACE
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
            is ChangePassword -> changePassword(action.value)
            is ShowPasswordClick -> changePasswordVisible()
            is ChangePasswordRepeat -> changeRepeatPassword(action.value)
            is ShowPasswordRepeatClick -> changeRepeatPasswordVisible()
            is ChangeEmail -> changeEmail(action.value)
            is ChangePhone -> changePhone(action.value)
            is CreateAccountClick -> createAccount()
            is OnBackClick -> returnToPreviousScreen.invoke()
        }
    }

    private fun changeLogin(login: String) {
        state.value = state.value.copy(login = login, error = EMPTY)
    }

    private fun changePassword(password: String) {
        state.value = state.value.copy(password = password, error = EMPTY)
    }

    private fun changePasswordVisible() {
        state.value = state.value.copy(isPasswordHidden = !state.value.isPasswordHidden)
    }

    private fun changeRepeatPassword(repeatPassword: String) {
        state.value = state.value.copy(passwordRepeat = repeatPassword, error = EMPTY)
    }

    private fun changeRepeatPasswordVisible() {
        state.value = state.value.copy(isPasswordRepeatHidden = !state.value.isPasswordRepeatHidden)
    }

    private fun changeEmail(email: String) {
        state.value = state.value.copy(email = email, error = EMPTY)
    }

    private fun changePhone(phone: String) {
        state.value = state.value.copy(phone = phone, error = EMPTY)
    }

    private fun createAccount() {
        if (isFieldsNotCorrect()) return
        componentScope.launch {
            state.value = state.value.copy(isLoading = true)
            repository.register(
                login = state.value.login.trim(),
                password = state.value.password.trim(),
                repeatPassword = state.value.passwordRepeat.trim(),
                email = state.value.email.trim(),
                phone = state.value.phone.trim()
            ).onSuccess {
                returnToPreviousScreen.invoke()
            }.onFailure {
                state.value = state.value.copy(isLoading = false, error = it.message)
            }
        }
    }

    private fun isFieldsNotCorrect(): Boolean {
        if (
            state.value.login.contains(SPACE) ||
            state.value.password.contains(SPACE) ||
            state.value.passwordRepeat.contains(SPACE) ||
            state.value.email.contains(SPACE) ||
            state.value.phone.contains(SPACE)
        ) {
            state.value = state.value.copy(error = AppResStrings.error_spaces)
            return true
        }

        if (state.value.login.length < LOGIN_MIN_LENGTH) {
            state.value = state.value.copy(error = AppResStrings.error_login_length.format(LOGIN_MIN_LENGTH.toString()))
            return true
        }

        if (state.value.password.length < PASSWORD_MIN_LENGTH) {
            state.value = state.value.copy(error = AppResStrings.error_password_length.format(PASSWORD_MIN_LENGTH.toString()))
            return true
        }

        if (state.value.password != state.value.passwordRepeat) {
            state.value = state.value.copy(error = AppResStrings.error_passwords_match)
            return true
        }

        if (state.value.email.length < EMAIL_MIN_LENGTH) {
            state.value = state.value.copy(error = AppResStrings.error_email_length.format(EMAIL_MIN_LENGTH.toString()))
            return true
        }

        if (state.value.phone.length < PHONE_MIN_LENGTH) {
            state.value = state.value.copy(error = AppResStrings.error_phone_length.format(PHONE_MIN_LENGTH.toString()))
        }

        return false
    }
}

interface AccountCreateComponent {
    val state: Value<AccountCreateState>
    fun doAction(action: AccountCreateAction)
}
