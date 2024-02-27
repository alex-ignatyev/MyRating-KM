package utils

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.core.component.KoinComponent

abstract class BaseComponent<E>(
    private val componentContext: ComponentContext
) : KoinComponent, ComponentContext by componentContext {

    private val _effect = Channel<E>()
    internal val effect = _effect.receiveAsFlow()

    internal val ComponentContext.componentScope: CoroutineScope
        get() {
            val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

            lifecycle.doOnDestroy {
                scope.cancel()
            }

            return scope
        }
}