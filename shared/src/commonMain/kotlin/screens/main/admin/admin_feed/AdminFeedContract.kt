package screens.main.admin.admin_feed

sealed class AdminFeedEvent {
    class InitAdminFeedScreen : AdminFeedEvent()
}

data class AdminFeedState(
    val isLoading: Boolean = true
)

sealed class AdminFeedAction
