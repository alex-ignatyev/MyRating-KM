package screens.auth.account_login

import cafe.adriel.voyager.core.screen.Screen
import utils.mvi.Action
import utils.mvi.Event

sealed class AccountLoginEvent : Event {
    data class ChangeLogin(val value: String) : AccountLoginEvent()
    data class ChangePassword(val value: String) : AccountLoginEvent()
    class ShowPasswordClick : AccountLoginEvent()
    class CreateAccountClick : AccountLoginEvent()
    class ForgotPasswordClick : AccountLoginEvent()
    class LoginClick : AccountLoginEvent()
    class ClearActions : AccountLoginEvent()
}

data class AccountLoginState(
    val isLoading: Boolean = false,
    val login: String = "",
    val password: String = "",
    val isPasswordHidden: Boolean = true,
    val error: String = ""
)

sealed class AccountLoginAction : Action {
    class OpenCreateAccountScreen : AccountLoginAction()
    class OpenForgotPasswordScreen : AccountLoginAction()
    class OpenMainScreen(val screen: Screen) : AccountLoginAction()
}
