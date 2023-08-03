package screens.main.profile.settings

import com.adeo.kviewmodel.BaseSharedViewModel
import data.SettingsDataSource
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import screens.main.profile.settings.SettingsAction.OpenLoginScreen
import screens.main.profile.settings.SettingsAction.ReturnBack
import screens.main.profile.settings.SettingsEvent.OnBackClick
import screens.main.profile.settings.SettingsEvent.OnLogOutClick

class SettingsViewModel : KoinComponent, BaseSharedViewModel<SettingsState, SettingsAction, SettingsEvent>(
    initialState = SettingsState()
) {

    private val settings: SettingsDataSource by inject()

    override fun obtainEvent(viewEvent: SettingsEvent) {
        when (viewEvent) {
            is OnLogOutClick -> logOut()
            is OnBackClick -> returnBack()
        }
    }

    private fun logOut() {
        viewModelScope.launch {
            settings.clear()
            viewAction = OpenLoginScreen()
        }
    }

    private fun returnBack() {
        viewAction = ReturnBack()
    }
}
