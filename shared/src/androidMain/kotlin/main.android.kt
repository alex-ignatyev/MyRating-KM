import RootComponent.RootScreen
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.moriatsushi.insetsx.safeArea
import com.moriatsushi.insetsx.systemBars
import data.LocalSettingsEventBus
import data.SettingsEventBus
import di.LocalPlatform
import di.Platform.Android
import navigation.FeedTab
import navigation.MixTab
import screens.auth.AuthComponent
import screens.auth.AuthComponent.AuthScreen
import screens.auth.account_create.AccountCreateScreen
import screens.auth.account_forgot.AccountForgotScreen
import screens.auth.account_login.AccountLoginScreen
import screens.main.MainComponent
import screens.main.MainComponent.MainScreen
import screens.main.feed.FeedScreen
import screens.main.profile.ProfileComponent
import screens.main.profile.ProfileComponent.ProfileScreen
import screens.main.profile.settings.SettingsScreen
import screens.main.profile.user.UserScreen
import screens.splash.SplashScreen
import ui.KalyanTheme
import ui.MainTheme
import ui.components.KalyanNavigationBar
import ui.components.TabNavigationItem

@Composable
fun MainView(component: DefaultRootComponent) {
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

            Surface(modifier = Modifier.fillMaxSize()) {
                Children(
                    stack = component.stack,
                    modifier = Modifier.fillMaxSize(),
                    //animation = stackAnimation(fade() + scale())
                ) {
                    when (val instance = it.instance) {
                        is RootScreen.Splash -> SplashScreen(component = instance.component)
                        is RootScreen.Login -> Auth(component = instance.component)
                        is RootScreen.Main -> Main(component = instance.component)
                    }
                }
            }
        }
    }
}

@Composable
private fun Auth(component: AuthComponent) {
    Children(
        stack = component.stack,
        modifier = Modifier.fillMaxSize()
    ) {
        when (val instance = it.instance) {
            is AuthScreen.Login -> AccountLoginScreen(instance.component)
            is AuthScreen.Create -> AccountCreateScreen(instance.component)
            is AuthScreen.Forgot -> AccountForgotScreen(instance.component)
        }
    }
}

@Composable
private fun Main(component: MainComponent) {
    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeArea.exclude(WindowInsets.systemBars)),
        content = { paddingValues ->
            Children(
                stack = component.stack,
                modifier = Modifier.fillMaxSize().padding(paddingValues)
            ) {
                when (val instance = it.instance) {
                    is MainScreen.Feed -> FeedScreen(instance.component)
                    is MainScreen.Profile -> ProfileScreen(instance.component)
                }
            }
        },
        bottomBar = {
            KalyanNavigationBar(onFloatingAction = {

            }) {
                var currentTab by remember { mutableIntStateOf(0) }

                TabNavigationItem(tab = FeedTab(), currentTab) { index ->
                    currentTab = index
                    component.navigateToFeed()
                }
                TabNavigationItem(MixTab(), currentTab) { index ->
                    currentTab = index
                    component.navigateToProfile()
                }
                //  TabNavigationItem(ProfileTab)
            }
        }
    )
}

@Composable
private fun ProfileScreen(component: ProfileComponent) {
    Children(
        stack = component.stack,
        modifier = Modifier.fillMaxSize()
    ) {
        when (val instance = it.instance) {
            is ProfileScreen.User -> UserScreen(instance.component)
            is ProfileScreen.Settings -> SettingsScreen(instance.component)
        }
    }
}
