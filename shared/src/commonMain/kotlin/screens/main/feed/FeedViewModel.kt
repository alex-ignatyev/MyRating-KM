package screens.main.feed

import com.adeo.kviewmodel.BaseSharedViewModel
import domain.repository.RatingRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.domain.Category
import model.domain.CategoryParent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import screens.main.feed.FeedAction.OpenCategoryInfoScreen
import screens.main.feed.FeedEvent.ClearActions
import screens.main.feed.FeedEvent.InitFeedScreen
import screens.main.feed.FeedEvent.OnAddRequest
import screens.main.feed.FeedEvent.OnCategoryClick
import screens.main.feed.FeedEvent.OnCategorySearch
import screens.main.feed.FeedEvent.OnDataRefresh
import screens.main.feed.FeedEvent.OnErrorRefresh
import screens.main.feed.FeedState.Data
import screens.main.feed.FeedState.Empty
import screens.main.feed.FeedState.Error
import screens.main.feed.FeedState.Loading
import utils.EMPTY
import utils.answer.onFailure
import utils.answer.onSuccess
import utils.mvi.Action
import utils.mvi.Event

class FeedViewModel : KoinComponent, BaseSharedViewModel<FeedState, Action, Event>(initialState = Loading()) {

    private val repository: RatingRepository by inject()
    private var search: String = EMPTY

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is InitFeedScreen -> fetchData()
            is OnCategorySearch -> onSearch(viewEvent.search)
            is OnDataRefresh -> onRefresh()
            is OnErrorRefresh -> onErrorRefresh()
            is OnCategoryClick -> openNextScreen(viewEvent.category)
            is OnAddRequest -> {} //TODO
            is ClearActions -> clearActions()
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            delay(1000L)
            repository.getFeed(search).onSuccess { response ->
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

    private fun openNextScreen(category: CategoryParent) {
        when (category) {
            is Category -> {
                if (category.subcategories.isEmpty()) {
                    viewAction = OpenCategoryInfoScreen(category.id) // TODO Должен открываться экран рейтинга
                } else {
                    viewState = Data(isLoading = false, data = category.subcategories)
                }
            }
        }
    }

    private fun clearActions() {
        viewAction = null
    }
}
