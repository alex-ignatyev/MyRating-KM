package screens.main.feed

import model.domain.Category

sealed interface FeedAction {
    data object InitFeedScreen : FeedAction
    data class OnCategorySearch(val search: String) : FeedAction
    data object OnDataRefresh : FeedAction
    data object OnErrorRefresh : FeedAction
    data class OnCategoryClick(val category: Category) : FeedAction
    data object OnAddRequest : FeedAction
}

sealed interface FeedState {
    data class Data(val data: List<Category> = emptyList()) : FeedState
    data object Loading : FeedState
    data class Empty(val isLoading: Boolean = true) : FeedState
    data class Error(val isLoading: Boolean = true) : FeedState
}

sealed class FeedEffect
