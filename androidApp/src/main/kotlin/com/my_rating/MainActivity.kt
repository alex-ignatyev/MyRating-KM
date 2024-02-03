package com.my_rating

import DefaultRootComponent
import MainView
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.defaultComponentContext
import di.PlatformConfiguration
import di.PlatformSDK

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        PlatformSDK.init(
            PlatformConfiguration(
                activityContext = applicationContext
            )
        )
        val root = DefaultRootComponent(componentContext = defaultComponentContext())

        setContent {
            MainView(root)
        }
    }
}
