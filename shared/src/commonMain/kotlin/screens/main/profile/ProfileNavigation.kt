package screens.main.profile

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import screens.main.profile.ProfileComponent.ProfileScreen
import screens.main.profile.change_password.ChangePasswordComponent
import screens.main.profile.change_password.DefaultChangePasswordComponent
import screens.main.profile.user.DefaultUserComponent
import screens.main.profile.user.UserComponent

class DefaultProfileComponent(
    componentContext: ComponentContext,
    private val openLoginScreen: () -> Unit
) : ProfileComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<ProfileScreenConfig>()

    override val stack: Value<ChildStack<*, ProfileScreen>> =
        childStack(
            source = navigation,
            serializer = ProfileScreenConfig.serializer(),
            initialConfiguration = ProfileScreenConfig.User,
            handleBackButton = true,
            childFactory = ::child,
        )

    private fun child(config: ProfileScreenConfig, childComponentContext: ComponentContext): ProfileScreen =
        when (config) {
            is ProfileScreenConfig.User -> ProfileScreen.User(
                DefaultUserComponent(
                    componentContext = childComponentContext,
                    openChangePasswordScreen = {
                        navigation.push(ProfileScreenConfig.ChangePassword)
                    },
                    openLoginScreen = openLoginScreen
                )
            )

            is ProfileScreenConfig.ChangePassword -> ProfileScreen.ChangePassword(
                DefaultChangePasswordComponent(
                    componentContext = childComponentContext,
                    returnToPreviousScreen = {
                        navigation.pop()
                    }
                )
            )

            else -> throw IllegalArgumentException()
        }

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }

    @Serializable
    sealed interface ProfileScreenConfig {
        @Serializable
        data object User : ProfileScreenConfig

        @Serializable
        data object ChangePassword : ProfileScreenConfig
    }
}

interface ProfileComponent {
    val stack: Value<ChildStack<*, ProfileScreen>>

    fun onBackClicked(toIndex: Int)

    @Serializable
    sealed class ProfileScreen {
        @Serializable
        class User(val component: UserComponent) : ProfileScreen()

        @Serializable
        class ChangePassword(val component: ChangePasswordComponent) : ProfileScreen()
    }
}
