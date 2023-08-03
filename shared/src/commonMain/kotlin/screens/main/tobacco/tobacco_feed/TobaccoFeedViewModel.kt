package screens.main.tobacco.tobacco_feed

import com.adeo.kviewmodel.BaseSharedViewModel
import domain.repository.RatingRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import screens.main.tobacco.tobacco_feed.TobaccoFeedAction.OpenTobaccoInfoScreen
import screens.main.tobacco.tobacco_feed.TobaccoFeedEvent.ClearActions
import screens.main.tobacco.tobacco_feed.TobaccoFeedEvent.InitTobaccoFeedScreen
import screens.main.tobacco.tobacco_feed.TobaccoFeedEvent.OnAddTobaccoRequest
import screens.main.tobacco.tobacco_feed.TobaccoFeedEvent.OnDataRefresh
import screens.main.tobacco.tobacco_feed.TobaccoFeedEvent.OnErrorRefresh
import screens.main.tobacco.tobacco_feed.TobaccoFeedEvent.OnTobaccoClick
import screens.main.tobacco.tobacco_feed.TobaccoFeedEvent.OnTobaccoSearch
import screens.main.tobacco.tobacco_feed.TobaccoFeedState.Data
import screens.main.tobacco.tobacco_feed.TobaccoFeedState.Empty
import screens.main.tobacco.tobacco_feed.TobaccoFeedState.Error
import screens.main.tobacco.tobacco_feed.TobaccoFeedState.Loading
import utils.EMPTY
import utils.answer.onFailure
import utils.answer.onSuccess
import utils.mvi.Action
import utils.mvi.Event

class TobaccoFeedViewModel : KoinComponent, BaseSharedViewModel<TobaccoFeedState, Action, Event>(initialState = Loading()) {

    private val repository: RatingRepository by inject()
    private var search: String = EMPTY

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is InitTobaccoFeedScreen -> fetchData()
            is OnTobaccoSearch -> onSearch(viewEvent.search)
            is OnDataRefresh -> onRefresh()
            is OnErrorRefresh -> onErrorRefresh()
            is OnTobaccoClick -> openTobaccoInfoScreen(viewEvent.tobaccoId)
            is OnAddTobaccoRequest -> {} //TODO
            is ClearActions -> clearActions()
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            delay(1000L)
            repository.getTobaccoFeed(search).onSuccess { response ->
                viewState = when {
                    response.isEmpty() -> Empty(isLoading = false)
                    else -> Data(isLoading = false, data = response)
                }
            }.onFailure {
                viewState = Error()
            }
        }
    }

    private fun onSearch(search: String = EMPTY) {
        this.search = search
        viewState = Data(isLoading = true)
        fetchData()
    }

    private fun onRefresh() {
        viewState = Data(isLoading = true)
        fetchData()
    }

    private fun onErrorRefresh() {
        viewState = Loading()
        fetchData()
    }

    private fun openTobaccoInfoScreen(tobaccoId: String) {
        viewAction = OpenTobaccoInfoScreen(tobaccoId)
    }

    private fun clearActions() {
        viewAction = null
    }
}
