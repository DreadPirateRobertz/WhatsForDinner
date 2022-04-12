package ramseybros.WhatsForDinner.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNav(
    val label: String,
    val icon: ImageVector,
    val route:String,
)

object Constants {
    val BottomNavItems = listOf(
        BottomNav(
            label = "Home",
            icon = Icons.Filled.Home,
            route = "home"
        ),
        BottomNav(
            label = "Search",
            icon = Icons.Filled.Search,
            route = "RecipeSearchScreen"
        ),
        BottomNav(
            label = "Saved Recipes",
            icon = Icons.Filled.Star,
            route = "saved"
        )
    )
}