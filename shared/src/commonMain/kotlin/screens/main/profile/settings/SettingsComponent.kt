package screens.main.profile.settings

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import data.remote.SettingsDataSource
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import screens.main.profile.settings.SettingsAction.OnBackClick
import screens.main.profile.settings.SettingsAction.OnLogOutClick
import utils.BaseComponent

class DefaultSettingsComponent(
    componentContext: ComponentContext,
    private val openLoginScreen: () -> Unit,
    private val onBackClick: () -> Unit
) : SettingsComponent, BaseComponent<SettingsEffect>(componentContext) {

    private val settings: SettingsDataSource by inject()

    override val state = MutableValue(SettingsState())

    override fun doAction(action: SettingsAction) {
        when (action) {
            is OnLogOutClick -> logOut()
            is OnBackClick -> onBackClick.invoke()
        }
    }

    private fun logOut() {
        componentScope.launch {
            val isCleared = settings.clear()
            if (isCleared)
                openLoginScreen.invoke()
        }
    }
}

interface SettingsComponent {
    val state: Value<SettingsState>
    fun doAction(action: SettingsAction)
}