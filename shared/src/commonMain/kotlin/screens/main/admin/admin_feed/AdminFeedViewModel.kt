package screens.main.admin.admin_feed

import com.adeo.kviewmodel.BaseSharedViewModel
import org.koin.core.component.KoinComponent
import screens.main.admin.admin_feed.AdminFeedEvent.InitAdminFeedScreen

class AdminFeedViewModel : KoinComponent, BaseSharedViewModel<AdminFeedState, AdminFeedAction, AdminFeedEvent>(
    initialState = AdminFeedState()
) {

    override fun obtainEvent(viewEvent: AdminFeedEvent) {
        when (viewEvent) {
            is InitAdminFeedScreen -> fetchData()
        }
    }

    private fun fetchData() {

    }
}
