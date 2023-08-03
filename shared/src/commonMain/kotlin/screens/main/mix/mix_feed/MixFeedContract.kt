package screens.main.mix.mix_feed

sealed class MixFeedEvent {
    class InitMixFeedScreen : MixFeedEvent()
}

data class MixFeedState(
    val isLoading: Boolean = true
)

sealed class MixFeedAction
