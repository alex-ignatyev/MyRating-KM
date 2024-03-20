package screens.main.profile.user

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import data.remote.SettingsDataSource
import domain.repository.AuthRepository
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import screens.main.profile.user.UserAction.ClickOnChangePassword
import screens.main.profile.user.UserAction.InitProfileScreen
import screens.main.profile.user.UserAction.OnLogOutClick
import utils.BaseComponent
import utils.answer.onFailure
import utils.answer.onSuccess

class DefaultUserComponent(
    componentContext: ComponentContext,
    private val openChangePasswordScreen: () -> Unit,
    private val openLoginScreen: () -> Unit
) : UserComponent, BaseComponent<UserEffect>(componentContext) {


    private val repository: AuthRepository by inject()
    private val settings: SettingsDataSource by inject()
    override val state = MutableValue(UserState())

    override fun doAction(action: UserAction) {
        when (action) {
            is InitProfileScreen -> fetchData()
            is OnLogOutClick -> logOut()
            is ClickOnChangePassword -> openChangePasswordScreen.invoke()
        }
    }

    private fun fetchData() {
        componentScope.launch {
            repository.getUserInfo().onSuccess {
                state.value = UserState(login = it.login, phone = "+${it.phone}", email = it.email)
            }.onFailure {

            }
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

interface UserComponent {
    val state: Value<UserState>
    fun doAction(action: UserAction)
}
