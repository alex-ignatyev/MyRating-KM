package screens.main.tobacco.tobacco_info

import model.data.tobacco.TobaccoVoteRequest.VoteType
import model.domain.TobaccoInfo

sealed class TobaccoInfoEvent {
    data class InitTobaccoInfoScreen(val tobaccoId: String) : TobaccoInfoEvent()
    data class VoteForTobacco(val type: VoteType, val value: Long) : TobaccoInfoEvent()
    class OnBackClick : TobaccoInfoEvent()
    class ClearActions : TobaccoInfoEvent()
}

data class TobaccoInfoState(
    val isLoading: Boolean = true,
    val data: TobaccoInfo = TobaccoInfo.EMPTY
)

sealed class TobaccoInfoAction {
    class ReturnBack : TobaccoInfoAction()
}
