import RootComponent.RootScreen
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import model.presentation.SplashFlow
import screens.auth.AuthComponent
import screens.auth.DefaultAuthComponent
import screens.main.DefaultMainComponent
import screens.main.MainComponent
import screens.splash.DefaultSplashComponent
import screens.splash.SplashComponent

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<RootScreenConfig>()

    override val stack: Value<ChildStack<*, RootScreen>> =
        childStack(
            source = navigation,
            serializer = RootScreenConfig.serializer(),
            initialConfiguration = RootScreenConfig.Splash,
            handleBackButton = true,
            childFactory = ::child,
        )

    private fun child(config: RootScreenConfig, childComponentContext: ComponentContext): RootScreen =
        when (config) {
            is RootScreenConfig.Splash -> RootScreen.Splash(DefaultSplashComponent(
                componentContext = childComponentContext
            ) { splashFlow ->
                when (splashFlow) {
                    is SplashFlow.AuthFlow -> navigation.replaceAll(RootScreenConfig.Login)
                    is SplashFlow.MainFlow -> navigation.replaceAll(RootScreenConfig.Main)
                }
            })

            is RootScreenConfig.Login -> RootScreen.Login(
                DefaultAuthComponent(
                    componentContext = childComponentContext,
                    openMainScreen = {
                        navigation.replaceAll(RootScreenConfig.Main)
                    })
            )

            is RootScreenConfig.Main -> RootScreen.Main(
                DefaultMainComponent(
                    componentContext = childComponentContext,
                    openLoginScreen = {
                        navigation.replaceAll(RootScreenConfig.Login)
                    })
            )

            else -> throw IllegalArgumentException()
        }

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }

    @Serializable
    sealed interface RootScreenConfig {
        @Serializable
        data object Splash : RootScreenConfig

        @Serializable
        data object Login : RootScreenConfig

        @Serializable
        data object Main : RootScreenConfig
    }
}

interface RootComponent {

    val stack: Value<ChildStack<*, RootScreen>>

    fun onBackClicked(toIndex: Int)

    @Serializable
    sealed class RootScreen {
        @Serializable
        class Splash(val component: SplashComponent) : RootScreen()

        @Serializable
        class Login(val component: AuthComponent) : RootScreen()

        @Serializable
        class Main(val component: MainComponent) : RootScreen()
    }
}