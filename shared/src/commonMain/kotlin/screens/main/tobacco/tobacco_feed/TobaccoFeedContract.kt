package screens.main.tobacco.tobacco_feed

import model.domain.TobaccoFeed
import utils.mvi.Action
import utils.mvi.Event

sealed class TobaccoFeedEvent : Event {
    class InitTobaccoFeedScreen : TobaccoFeedEvent()
    data class OnTobaccoSearch(val search: String) : TobaccoFeedEvent()
    class OnDataRefresh : TobaccoFeedEvent()
    class OnErrorRefresh : TobaccoFeedEvent()
    data class OnTobaccoClick(val tobaccoId: String) : TobaccoFeedEvent()
    class OnAddTobaccoRequest : TobaccoFeedEvent()
    class ClearActions : TobaccoFeedEvent()
}

sealed class TobaccoFeedState {
    data class Data(
        val isLoading: Boolean = true,
        val data: List<TobaccoFeed> = emptyList()
    ) : TobaccoFeedState()

    class Loading : TobaccoFeedState()
    data class Empty(val isLoading: Boolean = true) : TobaccoFeedState()
    data class Error(val isLoading: Boolean = true) : TobaccoFeedState()
}

sealed class TobaccoFeedAction : Action {
    class OpenTobaccoInfoScreen(val tobaccoId: String) : TobaccoFeedAction()
}
