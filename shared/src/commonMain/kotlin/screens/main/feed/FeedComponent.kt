package screens.main.feed

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import domain.repository.RatingRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import screens.main.feed.FeedAction.AddCategory
import screens.main.feed.FeedAction.InitFeedScreen
import screens.main.feed.FeedAction.OnAddRequest
import screens.main.feed.FeedAction.OnCategoryClick
import screens.main.feed.FeedAction.OnCategorySearch
import screens.main.feed.FeedAction.OnDataRefresh
import screens.main.feed.FeedAction.OnErrorRefresh
import screens.main.feed.FeedState.Data
import screens.main.feed.FeedState.Empty
import screens.main.feed.FeedState.Error
import screens.main.feed.FeedState.Loading
import utils.BaseComponent
import utils.EMPTY
import utils.answer.onFailure
import utils.answer.onSuccess

class DefaultFeedComponent(
    componentContext: ComponentContext,
    val openCategoryScreen: (String) -> Unit,
    val openAddCategoryScreen: () -> Unit
) : FeedComponent, BaseComponent<FeedEffect>(componentContext) {

    private val repository: RatingRepository by inject()

    override val state = MutableValue<FeedState>(Loading)
    private var shouldInit = true

    /* init {
            backHandler.register(BackCallback {
                toPrevious()
            })
     } */

    override fun doAction(action: FeedAction) {
        when (action) {
            is InitFeedScreen -> if (shouldInit) {
                shouldInit = false
                fetchData()
            }

            is AddCategory -> openAddCategoryScreen()
            is OnDataRefresh, is OnErrorRefresh -> fetchData()
            is OnCategorySearch -> fetchData(action.search)
            is OnCategoryClick -> openCategoryScreen(action.category.id)
            is OnAddRequest -> {} //TODO
        }
    }

    private fun fetchData(search: String = EMPTY) {
        state.value = Loading
        componentScope.launch {
            delay(1000L)
            repository.getCategories(search).onSuccess { response ->
                state.value = when {
                    response.isEmpty() -> Empty
                    else -> Data(data = response)
                }
            }.onFailure {
                state.value = Error
            }
        }
    }
}

interface FeedComponent {
    val state: Value<FeedState>
    fun doAction(action: FeedAction)
}