package screens.main.feed

import model.domain.Category
import model.domain.CategoryParent
import utils.mvi.Action
import utils.mvi.Event

sealed class FeedEvent : Event {
    class InitFeedScreen : FeedEvent()
    data class OnCategorySearch(val search: String) : FeedEvent()
    class OnDataRefresh : FeedEvent()
    class OnErrorRefresh : FeedEvent()
    data class OnCategoryClick(val category: CategoryParent) : FeedEvent()
    class OnAddRequest : FeedEvent()
    class ClearActions : FeedEvent()
}

sealed class FeedState {
    data class Data(
        val isLoading: Boolean = true,
        val data: List<CategoryParent> = emptyList()
    ) : FeedState()

    class Loading : FeedState()
    data class Empty(val isLoading: Boolean = true) : FeedState()
    data class Error(val isLoading: Boolean = true) : FeedState()
}

sealed class FeedAction : Action {
    class OpenCategoryInfoScreen(val categoryId: String) : FeedAction()
}
