package screens.main.profile.user

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import screens.main.profile.user.UserAction.ClickOnSettings
import screens.main.profile.user.UserAction.InitProfileScreen
import utils.BaseComponent

class DefaultUserComponent(
    componentContext: ComponentContext,
    private val openSettingsScreen: () -> Unit
) : UserComponent, BaseComponent<UserEffect>(componentContext) {

    override val state = MutableValue(UserState())

    override fun doAction(action: UserAction) {
        when (action) {
            is InitProfileScreen -> fetchData()
            is ClickOnSettings -> openSettingsScreen.invoke()
        }
    }

    private fun fetchData() {
        state.value = UserState(name = "Test Name", login = "@testname")
    }
}

interface UserComponent {
    val state: Value<UserState>
    fun doAction(action: UserAction)
}
