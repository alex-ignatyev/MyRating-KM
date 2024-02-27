package screens.splash

import com.arkivanov.decompose.ComponentContext
import data.remote.SettingsDataSource
import domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.presentation.SplashFlow
import model.presentation.SplashFlow.AuthFlow
import model.presentation.SplashFlow.MainFlow
import org.koin.core.component.inject
import utils.BaseComponent

class DefaultSplashComponent(
    componentContext: ComponentContext,
    private val navigateToNextScreen: (SplashFlow) -> Unit
) : SplashComponent, BaseComponent<SplashEffect>(componentContext) {

    private val authRepository: AuthRepository by inject()
    private val settings: SettingsDataSource by inject()

    override fun doAction(action: SplashAction) {
        when (action) {
            is SplashAction.InitScreen -> fetchAuthorization()
        }
    }

    private fun fetchAuthorization() {
        componentScope.launch {
            delay(1000L)
            if (settings.getUserLogin().isBlank()) {
                navigateToNextScreen(AuthFlow)
            } else {
                navigateToNextScreen(MainFlow)
            }
        }
    }
}

interface SplashComponent {
    fun doAction(action: SplashAction)
}
