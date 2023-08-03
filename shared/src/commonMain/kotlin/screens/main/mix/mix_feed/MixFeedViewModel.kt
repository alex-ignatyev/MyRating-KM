package screens.main.mix.mix_feed

import com.adeo.kviewmodel.BaseSharedViewModel
import org.koin.core.component.KoinComponent
import screens.main.mix.mix_feed.MixFeedEvent.InitMixFeedScreen

class MixFeedViewModel : KoinComponent, BaseSharedViewModel<MixFeedState, MixFeedAction, MixFeedEvent>(
    initialState = MixFeedState()
) {

    override fun obtainEvent(viewEvent: MixFeedEvent) {
        when (viewEvent) {
            is InitMixFeedScreen -> fetchData()
        }
    }

    private fun fetchData() {

    }
}
