package screens.auth.account_create

import utils.mvi.Action
import utils.mvi.Event

sealed class AccountCreateEvent : Event {
    data class ChangeLogin(val value: String) : AccountCreateEvent()
    data class ChangeName(val value: String) : AccountCreateEvent()
    data class ChangePassword(val value: String) : AccountCreateEvent()
    class ShowPasswordClick : AccountCreateEvent()
    data class ChangePasswordRepeat(val value: String) : AccountCreateEvent()
    class ShowPasswordRepeatClick : AccountCreateEvent()
    class CreateAccountClick : AccountCreateEvent()
    class OnBackClick : AccountCreateEvent()
    class ClearActions : AccountCreateEvent()
}

data class AccountCreateState(
    val isLoading: Boolean = false,
    val login: String = "",
    val name: String = "",
    val password: String = "",
    val isPasswordHidden: Boolean = true,
    val passwordRepeat: String = "",
    val isPasswordRepeatHidden: Boolean = true,
    val error: String = ""
)

sealed class AccountCreateAction : Action {
    class OpenLoginScreen : AccountCreateAction()
    class ReturnToPreviousScreen : AccountCreateAction()
}
