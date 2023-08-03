package screens.main.tobacco.tobacco_info

import com.adeo.kviewmodel.BaseSharedViewModel
import domain.repository.RatingRepository
import kotlinx.coroutines.launch
import model.data.tobacco.TobaccoVoteRequest.VoteType
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import screens.main.tobacco.tobacco_info.TobaccoInfoAction.ReturnBack
import screens.main.tobacco.tobacco_info.TobaccoInfoEvent.ClearActions
import screens.main.tobacco.tobacco_info.TobaccoInfoEvent.InitTobaccoInfoScreen
import screens.main.tobacco.tobacco_info.TobaccoInfoEvent.OnBackClick
import screens.main.tobacco.tobacco_info.TobaccoInfoEvent.VoteForTobacco
import utils.answer.onFailure
import utils.answer.onSuccess

class TobaccoInfoViewModel : KoinComponent, BaseSharedViewModel<TobaccoInfoState, TobaccoInfoAction, TobaccoInfoEvent>(
    initialState = TobaccoInfoState()
) {

    private val repo: RatingRepository by inject()
    private var tobaccoId: String = ""

    override fun obtainEvent(viewEvent: TobaccoInfoEvent) {
        when (viewEvent) {
            is InitTobaccoInfoScreen -> fetchData(viewEvent.tobaccoId)
            is VoteForTobacco -> voteForTobacco(viewEvent.type, viewEvent.value)
            is OnBackClick -> returnBack()
            is ClearActions -> clearActions()
        }
    }

    private fun fetchData(tobaccoId: String) {
        this.tobaccoId = tobaccoId
        viewModelScope.launch {
            repo.getTobaccoInfo(tobaccoId).onSuccess {
                viewState = viewState.copy(isLoading = false, data = it)
            }.onFailure {
                viewState = viewState.copy(isLoading = false)
            }
        }
    }

    private fun voteForTobacco(type: VoteType, value: Long) {
        viewModelScope.launch {
            repo.postTobaccoVote(
                tobaccoId = tobaccoId,
                type = type,
                value = value
            ).onSuccess {
                fetchData(tobaccoId)
            }
        }
    }

    private fun returnBack() {
        viewAction = ReturnBack()
    }

    private fun clearActions() {
        viewAction = null
    }
}
