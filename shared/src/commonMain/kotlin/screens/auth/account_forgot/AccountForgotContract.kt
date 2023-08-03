package screens.auth.account_forgot

import utils.mvi.Action
import utils.mvi.Event

sealed class AccountForgotEvent : Event {
    data class ChangeLogin(val value: String) : AccountForgotEvent()
    data class ChangePassword(val value: String) : AccountForgotEvent()
    class ShowPasswordClick : AccountForgotEvent()
    data class ChangePasswordRepeat(val value: String) : AccountForgotEvent()
    class ShowPasswordRepeatClick : AccountForgotEvent()
    class ResetPasswordClick : AccountForgotEvent()
    class OnBackClick : AccountForgotEvent()
    class ClearActions : AccountForgotEvent()
}

data class AccountForgotState(
    val isLoading: Boolean = false,
    val login: String = "",
    val password: String = "",
    val isPasswordHidden: Boolean = true,
    val passwordRepeat: String = "",
    val isPasswordRepeatHidden: Boolean = true,
    val error: String = ""
)

sealed class AccountForgotAction : Action {
    class OpenLoginScreen : AccountForgotAction()
    class ReturnToPreviousScreen : AccountForgotAction()
}
