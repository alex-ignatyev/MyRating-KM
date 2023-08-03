package navigation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import screens.main.profile.tobacco_add.AddTobaccoScreen
import ui.KalyanTheme
import ui.components.KalyanNavigationBar
import ui.components.TabNavigationItem

internal data class MainFlow(val isAdmin: Boolean) : Screen {

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        TabNavigator(TobaccoFeedTab) {
            val navigator = LocalNavigator.current

            Scaffold(
                content = {
                    BottomSheetNavigator(
                        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                        sheetBackgroundColor = KalyanTheme.colors.background
                    ) {
                        CurrentTab()
                    }
                },
                bottomBar = {
                    KalyanNavigationBar(onFloatingAction = {
                        navigator?.parent?.push(AddTobaccoScreen)
                    }) {
                        TabNavigationItem(TobaccoFeedTab)
                        TabNavigationItem(MixTab)
                        if (isAdmin) TabNavigationItem(AdminTab)
                        TabNavigationItem(ProfileTab)
                    }
                }
            )
        }
    }
}
