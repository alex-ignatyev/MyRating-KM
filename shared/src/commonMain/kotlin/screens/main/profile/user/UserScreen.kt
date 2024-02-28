package screens.main.profile.user

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import screens.main.profile.user.UserAction.InitProfileScreen

@Composable
fun UserScreen(component: UserComponent, rootModifier: Modifier = Modifier) {

    val state by component.state.subscribeAsState()

    ProfileView(state, rootModifier) {
        component.doAction(it)
    }

    component.doAction(InitProfileScreen)
}
