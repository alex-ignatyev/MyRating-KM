package screens.main.category.category_info

import com.adeo.kviewmodel.BaseSharedViewModel
import domain.repository.RatingRepository
import kotlinx.coroutines.launch
import model.data.tobacco.TobaccoVoteRequest.VoteType
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import screens.main.category.category_info.TobaccoInfoAction.ReturnBack
import screens.main.category.category_info.TobaccoInfoEvent.ClearActions
import screens.main.category.category_info.TobaccoInfoEvent.InitTobaccoInfoScreen
import screens.main.category.category_info.TobaccoInfoEvent.OnBackClick
import screens.main.category.category_info.TobaccoInfoEvent.VoteForTobacco

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

        }
    }

    private fun voteForTobacco(type: VoteType, value: Long) {
        viewModelScope.launch {

        }
    }

    private fun returnBack() {
        viewAction = ReturnBack()
    }

    private fun clearActions() {
        viewAction = null
    }
}
