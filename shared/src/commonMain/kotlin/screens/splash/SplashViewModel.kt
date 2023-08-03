package screens.splash

import com.adeo.kviewmodel.BaseSharedViewModel
import data.SettingsDataSource
import domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import navigation.MainFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import screens.auth.account_login.AccountLoginScreen
import screens.splash.SplashAction.OpenFlow
import screens.splash.SplashEvent.InitSplashScreen
import utils.answer.onFailure
import utils.answer.onSuccess

class SplashViewModel : KoinComponent, BaseSharedViewModel<SplashState, SplashAction, SplashEvent>(
    initialState = SplashState()
) {

    private val authRepository: AuthRepository by inject()
    private val settings: SettingsDataSource by inject()

    override fun obtainEvent(viewEvent: SplashEvent) {
        when (viewEvent) {
            is InitSplashScreen -> fetchAuthorization()
        }
    }

    private fun fetchAuthorization() {
        viewModelScope.launch {
            delay(1000L)
            val token = settings.getToken()
            if (token.isBlank()) {
                viewAction = OpenFlow(AccountLoginScreen)
            } else {
                authRepository.authorize().onSuccess {
                    viewAction = OpenFlow(MainFlow(settings.getAdmin()))
                }.onFailure {
                    settings.clear()
                    viewAction = OpenFlow(AccountLoginScreen)
                }
            }
        }
    }
}
