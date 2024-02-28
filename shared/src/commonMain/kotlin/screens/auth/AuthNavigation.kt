package screens.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import screens.auth.AuthNavigation.AuthScreen
import screens.auth.account_create.AccountCreateComponent
import screens.auth.account_create.DefaultAccountCreateComponent
import screens.auth.account_forgot.AccountForgotComponent
import screens.auth.account_forgot.DefaultAccountForgotComponent
import screens.auth.account_login.AccountLoginComponent
import screens.auth.account_login.DefaultAccountLoginComponent

class DefaultAuthComponent(
    componentContext: ComponentContext,
    private val openMainScreen: () -> Unit
) : AuthNavigation, ComponentContext by componentContext {

    private val navigation = StackNavigation<AuthScreenConfig>()

    override val stack: Value<ChildStack<*, AuthScreen>> =
        childStack(
            source = navigation,
            serializer = AuthScreenConfig.serializer(),
            initialConfiguration = AuthScreenConfig.Login,
            handleBackButton = true,
            childFactory = ::child,
        )

    private fun child(config: AuthScreenConfig, childComponentContext: ComponentContext): AuthScreen =
        when (config) {
            is AuthScreenConfig.Login -> AuthScreen.Login(
                DefaultAccountLoginComponent(
                    componentContext = childComponentContext,
                    openCreateAccountScreen = {
                        navigation.push(AuthScreenConfig.Create)
                    },
                    openForgotPasswordScreen = {
                        navigation.push(AuthScreenConfig.Forgot)
                    },
                    openMainScreen = openMainScreen
                )
            )

            is AuthScreenConfig.Create -> AuthScreen.Create(
                DefaultAccountCreateComponent(
                    componentContext = childComponentContext,
                    returnToPreviousScreen = {
                        navigation.pop()
                    }
                ))

            is AuthScreenConfig.Forgot -> AuthScreen.Forgot(
                DefaultAccountForgotComponent(
                    componentContext = childComponentContext,
                    returnToPreviousScreen = {
                        navigation.pop()
                    })
            )

            else -> throw IllegalArgumentException()
        }

    override fun onBackClicked(screenIndex: Int) {
        navigation.popTo(index = screenIndex)
    }

    @Serializable
    sealed interface AuthScreenConfig {
        @Serializable
        data object Login : AuthScreenConfig

        @Serializable
        data object Create : AuthScreenConfig

        @Serializable
        data object Forgot : AuthScreenConfig
    }
}

interface AuthNavigation {
    val stack: Value<ChildStack<*, AuthScreen>>

    fun onBackClicked(screenIndex: Int)

    @Serializable
    sealed class AuthScreen {
        @Serializable
        class Login(val component: AccountLoginComponent) : AuthScreen()

        @Serializable
        class Create(val component: AccountCreateComponent) : AuthScreen()

        @Serializable
        class Forgot(val component: AccountForgotComponent) : AuthScreen()
    }
}
