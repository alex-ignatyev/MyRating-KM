package screens.main.tobacco.tobacco_info.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import com.moriatsushi.insetsx.navigationBars
import model.data.tobacco.TobaccoVoteRequest.VoteType
import screens.main.tobacco.tobacco_info.TobaccoInfoEvent
import screens.main.tobacco.tobacco_info.TobaccoInfoEvent.VoteForTobacco
import ui.components.KalyanButton
import ui.components.KalyanDivider

data class VoteBottomSheet(val type: VoteType, val value: Long, val obtainEvent: (TobaccoInfoEvent) -> Unit) : Screen {

    @Composable
    override fun Content() {
        val bottomSheetNavigator = LocalBottomSheetNavigator.current
        var rate by remember { mutableStateOf(1) }

        Column(
            modifier = Modifier
                .windowInsetsPadding(
                    WindowInsets.navigationBars
                        .add(WindowInsets.navigationBars)
                        .add(WindowInsets(bottom = 48.dp))
                )
        ) {
            KalyanDivider(thickness = 3.dp, modifier = Modifier.width(32.dp).padding(top = 8.dp).align(Alignment.CenterHorizontally))

            Rating(value = value.toInt()) {
                rate = it
            }

            KalyanButton(text = type.name) {
                obtainEvent(VoteForTobacco(type, rate.toLong()))
                bottomSheetNavigator.hide()
            }
        }
    }
}
