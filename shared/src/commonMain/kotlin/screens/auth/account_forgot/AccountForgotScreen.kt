package screens.auth.account_forgot

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.adeo.kviewmodel.compose.ViewModel
import screens.auth.account_forgot.AccountForgotAction.OpenLoginScreen
import screens.auth.account_forgot.AccountForgotAction.ReturnToPreviousScreen
import screens.auth.account_forgot.AccountForgotEvent.ClearActions
import screens.auth.account_login.AccountLoginScreen

object AccountForgotScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        ViewModel(factory = { AccountForgotViewModel() }) { viewModel ->
            val state by viewModel.viewStates().collectAsState()
            val action by viewModel.viewActions().collectAsState(null)

            AccountForgotView(state) { event ->
                viewModel.obtainEvent(event)
            }

            when (action) {
                is OpenLoginScreen -> {
                    navigator.push(AccountLoginScreen)
                    viewModel.obtainEvent(ClearActions())
                }

                is ReturnToPreviousScreen -> navigator.pop()
                null -> {}
            }
        }
    }
}
