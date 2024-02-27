package screens.main.feed

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackCallback
import domain.repository.RatingRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.domain.Category
import org.koin.core.component.inject
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
    val openCategoryInfoScreen: (String) -> Unit
) : FeedComponent, BaseComponent<FeedEffect>(componentContext) {

    private val repository: RatingRepository by inject()

    override val state = MutableValue<FeedState>(Loading)
    private val categoriesStack = ArrayDeque<List<Category>>()

    init {
        backHandler.register(BackCallback {
            toPrevious()
        })
    }

    override fun doAction(action: FeedAction) {
        when (action) {
            is InitFeedScreen, is OnDataRefresh, is OnErrorRefresh -> fetchData()
            is OnCategorySearch -> fetchData(action.search)
            is OnCategoryClick -> toNext(action.category)
            is OnAddRequest -> {} //TODO
        }
    }

    private fun fetchData(search: String = EMPTY) {
        state.value = Loading
        componentScope.launch {
            delay(1000L)
            repository.getFeed(search).onSuccess { response ->
                state.value = when {
                    response.isEmpty() -> Empty(isLoading = false)
                    else -> {
                        categoriesStack.add(response)
                        Data(data = categoriesStack.first())
                    }
                }
            }.onFailure {
                state.value = Error()
            }
        }
    }

    private fun toNext(category: Category) {
        if (category.subcategories.isEmpty()) {
            openCategoryInfoScreen(category.id)
        } else {
            categoriesStack.add(category.subcategories)
            state.value = Data(data = categoriesStack.last())
        }
    }

    private fun toPrevious() {
        if (categoriesStack.isEmpty()) return
        categoriesStack.removeLast()
        state.value = Data(data = categoriesStack.last())
    }
}

interface FeedComponent {
    val state: Value<FeedState>
    fun doAction(action: FeedAction)
}