package screens.main.profile.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.adeo.kviewmodel.compose.ViewModel
import screens.auth.account_login.AccountLoginScreen
import screens.main.profile.settings.SettingsAction.OpenLoginScreen
import screens.main.profile.settings.SettingsAction.ReturnBack

object SettingsScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        ViewModel(factory = { SettingsViewModel() }) { viewModel ->
            val state by viewModel.viewStates().collectAsState()
            val action by viewModel.viewActions().collectAsState(null)

            SettingsView(state) {
                viewModel.obtainEvent(it)
            }

            when (action) {
                is OpenLoginScreen -> navigator.parent?.parent?.parent?.replaceAll(AccountLoginScreen)
                is ReturnBack -> navigator.pop()
                else -> {}
            }
        }
    }
}
