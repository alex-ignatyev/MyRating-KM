package screens.auth.account_create

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
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
import utils.EMPTY
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
            is OnBackClick -> returnToPreviousScreen()
        }
    }

    private fun changeLogin(login: String) {
        state.value = state.value.copy(login = login, error = EMPTY)
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
                returnToPreviousScreen()
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
            state.value = state.value.copy(error = "Can't use spaces")
            return true
        }
        return false
    }
}

interface AccountCreateComponent {
    val state: Value<AccountCreateState>
    fun doAction(action: AccountCreateAction)
}
