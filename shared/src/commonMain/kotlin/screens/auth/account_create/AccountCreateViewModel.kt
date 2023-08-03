package screens.auth.account_create

import com.adeo.kviewmodel.BaseSharedViewModel
import domain.repository.AuthRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import screens.auth.account_create.AccountCreateAction.OpenLoginScreen
import screens.auth.account_create.AccountCreateAction.ReturnToPreviousScreen
import screens.auth.account_create.AccountCreateEvent.ChangeLogin
import screens.auth.account_create.AccountCreateEvent.ChangeName
import screens.auth.account_create.AccountCreateEvent.ChangePassword
import screens.auth.account_create.AccountCreateEvent.ChangePasswordRepeat
import screens.auth.account_create.AccountCreateEvent.ClearActions
import screens.auth.account_create.AccountCreateEvent.CreateAccountClick
import screens.auth.account_create.AccountCreateEvent.OnBackClick
import screens.auth.account_create.AccountCreateEvent.ShowPasswordClick
import screens.auth.account_create.AccountCreateEvent.ShowPasswordRepeatClick
import utils.EMPTY
import utils.answer.onFailure
import utils.answer.onSuccess
import utils.mvi.Action
import utils.mvi.Event

class AccountCreateViewModel : KoinComponent, BaseSharedViewModel<AccountCreateState, Action, Event>(initialState = AccountCreateState()) {

    private val repository: AuthRepository by inject()

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is ChangeLogin -> changeLogin(viewEvent.value)
            is ChangeName -> changeName(viewEvent.value)
            is ChangePassword -> changePassword(viewEvent.value)
            is ShowPasswordClick -> changePasswordVisible()
            is ChangePasswordRepeat -> changeRepeatPassword(viewEvent.value)
            is ShowPasswordRepeatClick -> changeRepeatPasswordVisible()
            is CreateAccountClick -> createAccount()
            is OnBackClick -> returnToPreviousScreen()
            is ClearActions -> clearActions()
        }
    }

    private fun changeLogin(login: String) {
        viewState = viewState.copy(login = login, error = EMPTY)
    }

    private fun changeName(name: String) {
        viewState = viewState.copy(name = name, error = EMPTY)
    }

    private fun changePassword(password: String) {
        viewState = viewState.copy(password = password, error = EMPTY)
    }

    private fun changeRepeatPassword(repeatPassword: String) {
        viewState = viewState.copy(passwordRepeat = repeatPassword, error = EMPTY)
    }

    private fun changePasswordVisible() {
        val passwordVisible = !viewState.isPasswordHidden
        viewState = viewState.copy(isPasswordHidden = passwordVisible)
    }

    private fun changeRepeatPasswordVisible() {
        val passwordVisible = !viewState.isPasswordRepeatHidden
        viewState = viewState.copy(isPasswordRepeatHidden = passwordVisible)
    }

    private fun createAccount() {
        viewModelScope.launch {
            viewState = viewState.copy(isLoading = true)
            repository.create(
                login = viewState.login,
                name = viewState.name,
                password = viewState.password,
                repeatPassword = viewState.passwordRepeat
            ).onSuccess {
                viewAction = OpenLoginScreen()
            }.onFailure {
                viewState = viewState.copy(isLoading = false, error = it.message)
            }
        }
    }

    private fun returnToPreviousScreen() {
        viewAction = ReturnToPreviousScreen()
    }

    private fun clearActions() {
        viewAction = null
        viewState = viewState.copy(isLoading = false, error = EMPTY)
    }
}
