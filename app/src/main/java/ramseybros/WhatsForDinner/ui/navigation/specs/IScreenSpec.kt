package ramseybros.WhatsForDinner.ui.navigation.specs

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.Constants
import ramseybros.WhatsForDinner.ui.theme.colorDarkError
import ramseybros.WhatsForDinner.ui.theme.colorDarkSecondary
import ramseybros.WhatsForDinner.ui.theme.colorLightSecondary
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel

sealed interface IScreenSpec {
    val route: String
    val arguments: List<NamedNavArgument>
    val title: Int

    companion object {
        val start: String = SplashScreenSpec.route
        val map = IScreenSpec::class.sealedSubclasses
            .associate { it.objectInstance?.route to it.objectInstance }

        @Composable
        fun TopBar(navController: NavHostController, navBackStackEntry: NavBackStackEntry?) {
            val route = navBackStackEntry?.destination?.route ?: ""
            if (route != "") map[route]?.TopAppBarContent(
                navController = navController,
                navBackStackEntry
            )
            //&& route != "home"
        }

        @Composable
        fun BottomBar(navController: NavHostController) {
//            BottomAppBar(            // Defaults to null, that is, No cutout
//                cutoutShape = MaterialTheme.shapes.small.copy(
//                    CornerSize(percent = 50)
//                )
//            ) {
            var color = Color.White
            var bgColor = colorDarkSecondary
            if (isSystemInDarkTheme()) {
                color = Color.Black
                bgColor = colorLightSecondary
            }
            BottomNavigation(
                backgroundColor = bgColor,

                ) {
                // observe the backstack
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                // observe current route to change the icon
                // color,label color when navigated
                // observe current route to change the icon
                // color,label color when navigated
                val currentRoute = navBackStackEntry?.destination?.route
                // Bottom nav items we declared
                Constants.BottomNavItems.forEach { navItem ->
                    // Place the bottom nav items
                    BottomNavigationItem(
                        // it currentRoute is equal then its selected route
                        selected = currentRoute == navItem.route,
                        selectedContentColor = color,
                        unselectedContentColor = color.copy(.7f),
                        // navigate on click
                        onClick = {
                            navController.navigate(navItem.route) {
                                // Navigate to the "search” destination only if we’re not already on
                                // the "search" destination, avoiding multiple copies on the top of the
                                // back stack
                                launchSingleTop = true
                            }

                        },
                        // Icon of navItem
                        icon = {
                            if (navItem.label == "Kitchen") Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_kitchen_24),
                                contentDescription = navItem.label
                            )
                            else Icon(
                                imageVector = navItem.icon,
                                contentDescription = navItem.label
                            )
                        },
                        // label
                        label = {
                            Text(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 12.sp,
                                text = navItem.label
                            )
                        },
                        alwaysShowLabel = false
                    )
                }
            }

//            }
        }

        @Composable
        fun FloatingButton(navController: NavHostController) {
            FloatingActionButton(onClick = { navController.navigate(ShoppingListScreenSpec.navigateTo()) }) {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = null
                )
            }
        }
    }


    @Composable
    fun Content(
        viewModel: I_WhatsForDinnerViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry
    )

    @Composable
    fun TopAppBarActions(navController: NavHostController)

    @Composable
    private fun TopAppBarContent(
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?
    ) {
        var color = Color.White
        var bgColor = colorDarkSecondary
        if (isSystemInDarkTheme()) {
            color = Color.Black
            bgColor = colorLightSecondary
        }
        TopAppBar(
            title =
            {
                Row(
                    Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProvideTextStyle(value = MaterialTheme.typography.h6) {
                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.high,
                        ) {
                            if (navBackStackEntry?.destination?.route != "home") {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Start,
                                    maxLines = 1,
                                    text = stringResource(id = title),
                                    color = color
                                )
                            }
                        }
                    }
                    if (navBackStackEntry?.destination?.route == "home") {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = null,
                            tint = color
                        )
                    }
                }
            },
            backgroundColor = bgColor,
            modifier = Modifier.fillMaxWidth(),
            navigationIcon =
            if (navController.previousBackStackEntry != null) {
                {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = color
                        )
                    }
                }
            } else {
                null
            },
            actions = { TopAppBarActions(navController = navController) }
        )
    }

    fun navigateTo(vararg args: String?): String
}