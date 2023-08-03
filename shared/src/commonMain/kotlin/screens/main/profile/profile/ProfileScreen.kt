package screens.main.profile.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.adeo.kviewmodel.compose.ViewModel
import screens.main.profile.profile.ProfileAction.OpenSettingsScreen
import screens.main.profile.profile.ProfileEvent.ClearActions
import screens.main.profile.settings.SettingsScreen

object ProfileScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        ViewModel(factory = { ProfileViewModel() }) { viewModel ->
            val state by viewModel.viewStates().collectAsState()
            val action by viewModel.viewActions().collectAsState(null)

            ProfileView(state) {
                viewModel.obtainEvent(it)
            }

            when (action) {
                is OpenSettingsScreen -> {
                    navigator.push(SettingsScreen)
                    viewModel.obtainEvent(ClearActions())
                }

                else -> {}
            }
        }
    }
}
