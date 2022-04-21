package ramseybros.WhatsForDinner.ui.navigation.specs

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.NavigationItem
import ramseybros.WhatsForDinner.ui.screens.RecipeSearchScreen
import ramseybros.WhatsForDinner.ui.theme.colorDarkError
import ramseybros.WhatsForDinner.ui.theme.colorDarkSecondary
import ramseybros.WhatsForDinner.ui.theme.colorLightSecondary
import ramseybros.WhatsForDinner.ui.theme.colorLightTextSecondary
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


//        @OptIn(ExperimentalPagerApi::class)
//        @Composable
//        fun HorizontalSwiper() {
//            val pagerState = rememberPagerState()
//            HorizontalPager(
//                count = 3,
//                state = pagerState,
//                modifier = Modifier.fillMaxSize()
//            ) {
//                when (it) {
//                    0 -> RecipeSearchScreen {
//                        {}
//                    }
////                    1 -> navController.navigate(SavedRecipesScreenSpec.navigateTo())
////                    2 -> navController.navigate(RecipeSearchScreenSpec.navigateTo())
//                }
//            }
//        }
        @Composable
        fun BottomBar(navController: NavHostController) {
            BottomAppBar(            // Defaults to null, that is, No cutout
                modifier = Modifier.fillMaxWidth(),
                cutoutShape = MaterialTheme.shapes.small.copy(
                    CornerSize(percent = 50),

                )
            ) {
            var color = Color.White
            var bgColor = colorDarkSecondary
            if (isSystemInDarkTheme()) {
                color = Color.Black
                bgColor = colorLightSecondary
            }
            BottomNavigation(
                backgroundColor = bgColor,


                ) {
                    val items = listOf(
                        NavigationItem.SavedRecipes,
                        NavigationItem.Kitchen,
                        NavigationItem.Blank,
                        NavigationItem.Home,
                        NavigationItem.RecipeSearch,


                    )
                    // observe the backstack
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    // observe current route to change the icon
                    // color,label color when navigated
                    // observe current route to change the icon
                    // color,label color when navigated
                    val currentRoute = navBackStackEntry?.destination?.route
                    // Bottom nav items we declared
                    items.forEachIndexed { index, navItem ->
                        if (index != 2) {
                            // Place the bottom nav items
                            BottomNavigationItem(
                                // it currentRoute is equal then its selected route
                                selected = currentRoute == navItem.route,
                                selectedContentColor = color,
                                unselectedContentColor = color.copy(.6f),
                                // navigate on click
                                onClick = {
                                    navController.navigate(navItem.route) {
                                        popUpTo(navItem.route) {
                                            //savestate = true was disabling buttons
                                        }
                                        // Navigate to the "search” destination only if we’re not already on
                                        // the "search" destination, avoiding multiple copies on the top of the
                                        // back stack
                                        launchSingleTop = true
                                    }
                                },
                                // Icon of navItem
                                icon = {
                                    Row(horizontalArrangement = Arrangement.Start) {


                                        Icon(
                                            painter = painterResource(id = navItem.icon),
                                            contentDescription = navItem.title
                                        )
                                    }
                                },
                                // label
                                label = {
                                    Text(
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 12.sp,
                                        text = navItem.title
                                    )
                                },
                                alwaysShowLabel = false
                            )
                        }
                        else{
                            BottomNavigationItem(
                                icon = {},
                                label = {  },
                                selected = false,
                                onClick = {  },
                                enabled = false
                            )
                        }
                    }

                }

            }
        }


        @Composable
        fun FloatingButton(navController: NavHostController) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            var color: Color = Color.Black
            if (isSystemInDarkTheme()) color = colorLightSecondary
            FloatingActionButton(
                elevation = FloatingActionButtonDefaults.elevation(8.dp),
                contentColor = colorResource(id = R.color.teal_200),
                backgroundColor = color,
                onClick = { navController.navigate(ShoppingListScreenSpec.navigateTo())
            { launchSingleTop = true
                popUpTo(ShoppingListScreenSpec.route)
            }
            }) {
                if(navBackStackEntry?.destination?.route != "ShoppingList") {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = null
                    )
                }
                else{
                    Icon( //TODO: Will have to do a separate OnClick for adding an item to list
                        painter = painterResource(id = R.drawable.ic_baseline_add_shopping_cart_24),
                        contentDescription = null
                    )

                }
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
//                            if (navBackStackEntry?.destination?.route != "home") {
//                                if (navBackStackEntry?.destination?.route != "saved")
//                                    if (navBackStackEntry?.destination?.route != "MyKitchenScreen")
//                                        if (navBackStackEntry?.destination?.route != "RecipeSearchScreen")
                        Column() {
                            Text(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 30.sp,
                                fontFamily = FontFamily.Cursive,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                text = stringResource(id = title),
                                color = color
                            )
                            var padding = 0.dp
                            if (navBackStackEntry?.destination?.route == "home") padding = 16.dp
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = padding),
                                thickness = 2.dp,
                                color = colorResource(R.color.teal_200),
                            )
                        }
//                            }
                        }
                    }
//                    if (navBackStackEntry?.destination?.route == "home") {
//                        Column(horizontalAlignment = Alignment.End, modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(end = 12.dp)) {
//                            Icon(
//                                imageVector = Icons.Filled.Home,
//                                contentDescription = null,
//                                tint = color
//                            )
//                        }
//                    }
//                    else if (navBackStackEntry?.destination?.route == "saved") {
//                        Icon(
//                            imageVector = Icons.Filled.Star,
//                            contentDescription = null,
//                            tint = color
//                        )
//                    }
//                    else if (navBackStackEntry?.destination?.route == "MyKitchenScreen") {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_baseline_kitchen_24),
//                            contentDescription = null,
//                            tint = color
//                        )
//                    }
//                    else if (navBackStackEntry?.destination?.route == "RecipeSearchScreen") {
//                        Icon(
//                            imageVector = Icons.Filled.Search,
//                            contentDescription = null,
//                            tint = color
//                        )
//                    }
                }
            },
            backgroundColor = bgColor,
            modifier = Modifier.fillMaxWidth(),
            navigationIcon =
            if (navController.previousBackStackEntry != null) {
                {
                    IconButton(onClick = { navController.navigateUp()}) {
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
            actions = { TopAppBarActions(navController = navController) },
        )
    }

    fun navigateTo(vararg args: String?): String
}