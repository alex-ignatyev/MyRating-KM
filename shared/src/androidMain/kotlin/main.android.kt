import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import data.LocalSettingsEventBus
import data.SettingsEventBus
import di.LocalPlatform
import di.Platform.Android
import navigation.RootFlow
import ui.MRTheme

@Composable
fun MainView(component: DefaultRootComponent) {
    val systemUiController = rememberSystemUiController()
    val settingsEventBus = remember { SettingsEventBus() }
    val currentSettings = settingsEventBus.currentSettings.collectAsState().value

    MRTheme(
        darkTheme = currentSettings.isDarkMode
    ) {

        systemUiController.setSystemBarsColor(color = MRTheme.colors.background)

        CompositionLocalProvider(
            LocalPlatform provides Android,
            LocalSettingsEventBus provides settingsEventBus
        ) {
            RootFlow(component)
        }
    }
}
