package navigation

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import screens.main.MainComponent
import screens.main.MainComponent.MainScreen.FeedFlow
import screens.main.MainComponent.MainScreen.ProfileFlow
import ui.components.FeedTab
import ui.components.MRNavigationBar
import ui.components.ProfileTab
import ui.components.TabNavigationItem

@Composable
internal fun MainScreensFlow(component: MainComponent, rootModifier: Modifier = Modifier) {

    var currentTab by remember { mutableIntStateOf(0) }

    Scaffold(
        content = { paddingValues ->
            Children(
                stack = component.stack,
                animation = stackAnimation(
                    fade() + scale(
                        animationSpec = tween(durationMillis = 400),
                        frontFactor = 0.95F,
                        backFactor = 1.15F
                    )
                )
            ) {
                when (val instance = it.instance) {
                    is FeedFlow -> {
                        currentTab = 0
                        FeedFlow(instance.component, rootModifier.padding(paddingValues))
                    }

                    is ProfileFlow -> {
                        currentTab = 1
                        ProfileFlow(instance.component, rootModifier.padding(paddingValues))
                    }
                }
            }
        },
        bottomBar = {
            MRNavigationBar {
                TabNavigationItem(tab = FeedTab(), currentTab) {
                    component.navigateToFeedFlow()
                }
                TabNavigationItem(tab = ProfileTab(), currentTab) {
                    component.navigateToProfileFlow()
                }
            }
        }
    )
}
