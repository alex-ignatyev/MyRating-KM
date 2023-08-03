package screens.auth.account_forgot

import com.adeo.kviewmodel.BaseSharedViewModel
import domain.repository.AuthRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import screens.auth.account_forgot.AccountForgotAction.OpenLoginScreen
import screens.auth.account_forgot.AccountForgotAction.ReturnToPreviousScreen
import screens.auth.account_forgot.AccountForgotEvent.ChangeLogin
import screens.auth.account_forgot.AccountForgotEvent.ChangePassword
import screens.auth.account_forgot.AccountForgotEvent.ChangePasswordRepeat
import screens.auth.account_forgot.AccountForgotEvent.ClearActions
import screens.auth.account_forgot.AccountForgotEvent.OnBackClick
import screens.auth.account_forgot.AccountForgotEvent.ResetPasswordClick
import screens.auth.account_forgot.AccountForgotEvent.ShowPasswordClick
import screens.auth.account_forgot.AccountForgotEvent.ShowPasswordRepeatClick
import utils.EMPTY
import utils.answer.onFailure
import utils.answer.onSuccess
import utils.mvi.Action
import utils.mvi.Event

class AccountForgotViewModel : KoinComponent, BaseSharedViewModel<AccountForgotState, Action, Event>(initialState = AccountForgotState()) {

    private val repository: AuthRepository by inject()

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is ChangeLogin -> changeLogin(viewEvent.value)
            is ChangePassword -> changeNewPassword(viewEvent.value)
            is ShowPasswordClick -> changeNewPasswordVisibility()
            is ChangePasswordRepeat -> changeRepeatNewPassword(viewEvent.value)
            is ShowPasswordRepeatClick -> changeRepeatNewPasswordVisibility()
            is ResetPasswordClick -> resetPassword()
            is OnBackClick -> returnToPreviousScreen()
            is ClearActions -> clearActions()
        }
    }

    private fun changeLogin(login: String) {
        viewState = viewState.copy(login = login, error = EMPTY)
    }

    private fun changeNewPassword(newPassword: String) {
        viewState = viewState.copy(password = newPassword, error = EMPTY)
    }

    private fun changeRepeatNewPassword(repeatNewPassword: String) {
        viewState = viewState.copy(passwordRepeat = repeatNewPassword, error = EMPTY)
    }

    private fun changeNewPasswordVisibility() {
        val passwordVisible = !viewState.isPasswordHidden
        viewState = viewState.copy(isPasswordHidden = passwordVisible)
    }

    private fun changeRepeatNewPasswordVisibility() {
        val passwordVisible = !viewState.isPasswordRepeatHidden
        viewState = viewState.copy(isPasswordRepeatHidden = passwordVisible)
    }

    private fun resetPassword() {
        viewModelScope.launch {
            viewState = viewState.copy(isLoading = true)
            repository.forgot(
                login = viewState.login,
                newPassword = viewState.password,
                repeatNewPassword = viewState.passwordRepeat
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
