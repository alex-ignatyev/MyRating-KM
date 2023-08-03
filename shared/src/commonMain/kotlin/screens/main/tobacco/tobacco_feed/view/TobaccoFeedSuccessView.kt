package screens.main.tobacco.tobacco_feed.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import screens.main.tobacco.tobacco_feed.TobaccoFeedEvent.OnTobaccoClick
import screens.main.tobacco.tobacco_feed.TobaccoFeedState
import utils.mvi.Event

@Composable
fun TobaccoFeedSuccessView(state: TobaccoFeedState.Data, obtainEvent: (Event) -> Unit) {
    if (state.isLoading) {
        TobaccoFeedLoadingView()
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            contentPadding = PaddingValues(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(state.data) { index, item ->
                TobaccoView(item, index + 1, Modifier.clickable {
                    obtainEvent.invoke(OnTobaccoClick(item.id))
                })
            }
        }
    }
}
