package di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import di.Platform.Android

enum class Platform {
    Android, iOS
}

internal val LocalPlatform = staticCompositionLocalOf<Platform?> { null }

val currentPlatform: Platform
    @Composable get() = LocalPlatform.current ?: Android

expect class PlatformConfiguration {
    val platform: Platform
}
