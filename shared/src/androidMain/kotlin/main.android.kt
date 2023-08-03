import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import data.LocalSettingsEventBus
import data.SettingsEventBus
import di.LocalPlatform
import di.Platform.Android
import screens.splash.SplashScreen
import ui.KalyanTheme
import ui.MainTheme

@Composable
fun MainView() {
    val systemUiController = rememberSystemUiController()
    val settingsEventBus = remember { SettingsEventBus() }
    val currentSettings = settingsEventBus.currentSettings.collectAsState().value

    MainTheme(
        darkTheme = currentSettings.isDarkMode
    ) {

        systemUiController.setSystemBarsColor(color = KalyanTheme.colors.background)

        CompositionLocalProvider(
            LocalPlatform provides Android,
            LocalSettingsEventBus provides settingsEventBus
        ) {
            Navigator(SplashScreen)
        }
    }
}
