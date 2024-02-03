package model.presentation

sealed interface SplashFlow {
    data object AuthFlow : SplashFlow
    data object MainFlow : SplashFlow
}
