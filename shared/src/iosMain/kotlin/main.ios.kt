import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import cafe.adriel.voyager.navigator.Navigator
import data.LocalSettingsEventBus
import data.SettingsEventBus
import di.LocalPlatform
import di.Platform.iOS
import platform.UIKit.UIViewController
import screens.splash.SplashScreen
import ui.MainTheme

fun MainViewController(): UIViewController = ComposeUIViewController {
    val settingsEventBus = remember { SettingsEventBus() }
    val currentSettings = settingsEventBus.currentSettings.collectAsState().value

    MainTheme(
        darkTheme = currentSettings.isDarkMode
    ) {
        CompositionLocalProvider(
            LocalPlatform provides iOS,
            LocalSettingsEventBus provides settingsEventBus
        ) {
            Navigator(SplashScreen)
        }
    }
}
