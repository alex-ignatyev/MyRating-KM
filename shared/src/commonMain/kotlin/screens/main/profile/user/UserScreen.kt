package screens.main.profile.user

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@Composable
fun UserScreen(component: UserComponent) {

    val state by component.state.subscribeAsState()

    ProfileView(state) {
        component.doAction(it)
    }
}
