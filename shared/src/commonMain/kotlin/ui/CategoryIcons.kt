package ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CurrencyBitcoin
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.DryCleaning
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

fun getIcons(): List<ImageVector> {
    return listOf(
        Icons.Default.Star,
        Icons.Default.Home,
        Icons.Default.Favorite,
        Icons.Default.Person,
        Icons.Default.Public,
        Icons.Default.Category,
        Icons.Default.CurrencyBitcoin,
        Icons.Default.BusinessCenter,
        Icons.Default.Fastfood,
        Icons.Default.Park,
        Icons.Default.DryCleaning,
        Icons.Default.DirectionsCar
    )
}
