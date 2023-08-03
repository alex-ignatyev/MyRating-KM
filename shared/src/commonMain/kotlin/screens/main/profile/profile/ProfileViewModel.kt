package screens.main.profile.profile

import com.adeo.kviewmodel.BaseSharedViewModel
import screens.main.profile.profile.ProfileAction.OpenSettingsScreen
import screens.main.profile.profile.ProfileEvent.ClearActions
import screens.main.profile.profile.ProfileEvent.ClickOnSettings
import screens.main.profile.profile.ProfileEvent.InitProfileScreen

class ProfileViewModel : BaseSharedViewModel<ProfileState, ProfileAction, ProfileEvent>(
    initialState = ProfileState()
) {

    override fun obtainEvent(viewEvent: ProfileEvent) {
        when (viewEvent) {
            is InitProfileScreen -> fetchData()
            is ClickOnSettings -> openSettingsScreen()
            is ClearActions -> clearActions()
        }
    }

    private fun fetchData() {

    }

    private fun openSettingsScreen() {
        viewAction = OpenSettingsScreen()
    }

    private fun clearActions() {
        viewAction = null
    }
}
