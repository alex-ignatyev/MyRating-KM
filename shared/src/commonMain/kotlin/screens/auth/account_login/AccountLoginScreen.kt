package screens.auth.account_login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.adeo.kviewmodel.compose.ViewModel
import screens.auth.account_create.AccountCreateScreen
import screens.auth.account_forgot.AccountForgotScreen
import screens.auth.account_login.AccountLoginAction.OpenCreateAccountScreen
import screens.auth.account_login.AccountLoginAction.OpenForgotPasswordScreen
import screens.auth.account_login.AccountLoginAction.OpenMainScreen
import screens.auth.account_login.AccountLoginEvent.ClearActions

object AccountLoginScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        ViewModel(factory = { AccountLoginViewModel() }) { viewModel ->
            val state by viewModel.viewStates().collectAsState()
            val action by viewModel.viewActions().collectAsState(null)

            AccountLoginView(state) { event ->
                viewModel.obtainEvent(event)
            }

            when (action) {
                is OpenMainScreen -> {
                    navigator.replaceAll((action as OpenMainScreen).screen)
                    viewModel.obtainEvent(ClearActions())
                }

                is OpenCreateAccountScreen -> {
                    navigator.push(AccountCreateScreen)
                    viewModel.obtainEvent(ClearActions())
                }

                is OpenForgotPasswordScreen -> {
                    navigator.push(AccountForgotScreen)
                    viewModel.obtainEvent(ClearActions())
                }

                null -> {}
            }
        }
    }
}
