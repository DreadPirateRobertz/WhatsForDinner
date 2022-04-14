package ramseybros.WhatsForDinner.data


import androidx.compose.foundation.Image
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.ui.screens.MyKitchen

data class BottomNav(
    val label: String,
    var icon: ImageVector,
    val route: String,
)

object Constants {


    val BottomNavItems = listOf(
        BottomNav(
            label = "Saved Recipes",
            icon = Icons.Filled.Star,
            route = "saved"
        ),
        BottomNav(
            label = "Kitchen",
            icon = Icons.Filled.Star,
            route = "MyKitchenScreen"
        ),
        BottomNav(
            label = "Recipe Search",
            icon = Icons.Filled.Search,
            route = "RecipeSearchScreen"
        ),

        )
}


