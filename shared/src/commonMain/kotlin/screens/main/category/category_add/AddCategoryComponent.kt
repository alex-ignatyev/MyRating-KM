package screens.main.category.category_add

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import domain.repository.RatingRepository
import org.koin.core.component.inject
import screens.main.category.category_add.AddCategoryAction.AddCategory
import screens.main.category.category_add.AddCategoryAction.OnBackClick
import screens.main.category.category_add.AddCategoryAction.OnCategoryChange
import utils.BaseComponent

class DefaultAddCategoryComponent(
    componentContext: ComponentContext,
    private val returnToPreviousScreen: () -> Unit
) : AddCategoryComponent, BaseComponent<AddCategoryEffect>(componentContext) {

    private val repository: RatingRepository by inject()

    override val state = MutableValue(AddCategoryState())

    override fun doAction(action: AddCategoryAction) {
        when (action) {
            is OnBackClick -> returnToPreviousScreen.invoke()
            is OnCategoryChange -> changeCategory(action.value)
            is AddCategory -> addCategory()
        }
    }

    private fun changeCategory(title: String) {
        state.value = state.value.copy(title = title, isButtonEnabled = isButtonEnabled())
    }

    private fun addCategory() {
        // TODO Запрос на добавление категрии
        returnToPreviousScreen.invoke()
    }

    private fun isButtonEnabled(): Boolean {
        return state.value.title.length > 3
    }
}


interface AddCategoryComponent {
    val state: Value<AddCategoryState>
    fun doAction(action: AddCategoryAction)
}
