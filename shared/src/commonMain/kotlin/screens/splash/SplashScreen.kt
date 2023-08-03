package screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.adeo.kviewmodel.compose.ViewModel
import com.my_rating.shared.strings.AppResStrings
import screens.splash.SplashAction.OpenFlow
import screens.splash.SplashEvent.InitSplashScreen
import ui.KalyanTheme

object SplashScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        ViewModel({ SplashViewModel() }) { viewModel ->
            val action by viewModel.viewActions().collectAsState(null)
            Column(
                modifier = Modifier.fillMaxSize().background(KalyanTheme.colors.background),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = AppResStrings.app_name,
                    style = KalyanTheme.typography.header,
                    color = KalyanTheme.colors.backgroundOn,
                    textAlign = TextAlign.Center
                )
            }

            LaunchedEffect(Unit) {
                viewModel.obtainEvent(InitSplashScreen())
            }

            when (action) {
                is OpenFlow -> navigator.replace((action as OpenFlow).screen)
                else -> {}
            }
        }
    }
}
