package ramseybros.WhatsForDinner.ui.navigation.specs

import android.content.Context
import android.util.Log


import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.NavigationItem
import ramseybros.WhatsForDinner.ui.theme.colorDarkSecondary
import ramseybros.WhatsForDinner.ui.theme.colorLightSecondary
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel


sealed interface IScreenSpec {
    val route: String
    val arguments: List<NamedNavArgument>
    val title: Int

    companion object {
        val start: String = SplashScreenSpec.route
        val allScreens = IScreenSpec::class.sealedSubclasses
            .associate { it.objectInstance?.route to it.objectInstance }

        @Composable
        fun TopBar(
            navController: NavHostController,
            navBackStackEntry: NavBackStackEntry?,
            viewModel: I_WhatsForDinnerViewModel
        ) {
            val route = navBackStackEntry?.destination?.route ?: ""
            if (route != "") allScreens[route]?.TopAppBarContent(
                navController = navController,
                navBackStackEntry,
                viewModel = viewModel
            )


        }

        @Composable
        fun BottomBar(navController: NavHostController, navBackStackEntry: NavBackStackEntry?, viewModel: I_WhatsForDinnerViewModel) {
            val route = navBackStackEntry?.destination?.route ?: ""
            if (route != "") allScreens[route]?.BottomAppBarContent(
                navController = navController,
                navBackStackEntry,
                viewModel
            )
        }
        @Composable
        fun FAB(navController: NavHostController, viewModel: I_WhatsForDinnerViewModel, navBackStackEntry: NavBackStackEntry?) {
            val route = navBackStackEntry?.destination?.route ?: ""
            if (route != "") allScreens[route]?.FAB_Content(
                navController = navController,
                viewModel = viewModel,
                navBackStackEntry
            )

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



    }


    @Composable
    fun Content(
        viewModel: I_WhatsForDinnerViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry
    )

    @Composable
    fun TopAppBarActions(navController: NavHostController, viewModel: I_WhatsForDinnerViewModel)

    @Composable
    private fun TopAppBarContent(
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?,
        viewModel: I_WhatsForDinnerViewModel
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
                                Divider(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(end = 16.dp),
                                    thickness = 2.dp,
                                    color = colorResource(R.color.teal_200),
                                )
                            }
//                            }
                        }
                    }
                }
            },
            backgroundColor = bgColor,
            modifier = Modifier.fillMaxWidth(),
//            navigationIcon =
//            if (navController.previousBackStackEntry != null) {
//                {
//                    IconButton(onClick = { navController.navigateUp()}) {
//                        Icon(
//                            imageVector = Icons.Filled.ArrowBack,
//                            contentDescription = null,
//                            tint = color
//                        )
//                    }
//                }
//            } else {
//                null
//            },
            actions = { TopAppBarActions(navController = navController, viewModel = viewModel) },
        )
    }

    @Composable
    private fun BottomAppBarContent(
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?,
        viewModel: I_WhatsForDinnerViewModel
    ) {
        var color = Color.White
        var bgColor = colorDarkSecondary
        if (isSystemInDarkTheme()) {
            color = Color.Black
            bgColor = colorLightSecondary
        }
        BottomAppBar(            // Defaults to null, that is, No cutout
            cutoutShape = MaterialTheme.shapes.small.copy(
                CornerSize(percent = 50)),
            backgroundColor = bgColor
        ) {
            Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                BottomNavigation(
                    backgroundColor = bgColor,
                    modifier = Modifier.fillMaxSize(),
                    ) {
                    val items = listOf(
                        NavigationItem.SavedRecipes,
                        NavigationItem.Kitchen,
                        NavigationItem.Blank,
                        NavigationItem.Home,
                        NavigationItem.RecipeSearch,
                    )
                    val currentRoute = navBackStackEntry?.destination?.route
                    items.forEachIndexed { index, navItem ->
                        if (index != 2) {
                            BottomNavigationItem(
                                selected = currentRoute == navItem.route,
                                selectedContentColor = color,
                                unselectedContentColor = color.copy(.6f),
                                onClick = {
                                    if(navItem.route == HomeScreenSpec.route) viewModel.onHomeFlag = true
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
                                icon = {
                                    Row(horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            painter = painterResource(id = navItem.icon),
                                            contentDescription = navItem.title)
                                    }
                                },
                                label = {
                                    Text(
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 12.sp,
                                        text = navItem.title
                                    )
                                },
                                alwaysShowLabel = false
                            )
                        } else {
                            BottomNavigationItem(
                                icon = {},
                                label = { },
                                selected = false,
                                onClick = { },
                                enabled = false
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun FAB_Content(navController: NavHostController, viewModel: I_WhatsForDinnerViewModel, navBackStackEntry: NavBackStackEntry?) {
        var color: Color = Color.Black
        if (isSystemInDarkTheme()) color = colorLightSecondary
        FloatingActionButton(
            elevation = FloatingActionButtonDefaults.elevation(8.dp),
            contentColor = colorResource(id = R.color.teal_200),
            backgroundColor = color,
            onClick = {
                if (navBackStackEntry?.destination?.route != ShoppingListScreenSpec.route && navBackStackEntry?.destination?.route != LargeRecipeScreenSpec.route) {
                    //Current form will not let you nav from LargeRecipeScreen to Shopping list, will fix
                    navController.navigate(ShoppingListScreenSpec.navigateTo())
                    {
                        launchSingleTop = true
                        popUpTo(ShoppingListScreenSpec.route)
                    }
                }
            }

        ) {
            val context: Context = LocalContext.current
            if (navBackStackEntry?.destination?.route != ShoppingListScreenSpec.route && navBackStackEntry?.destination?.route != LargeRecipeScreenSpec.route) {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = null
                )
            } else {
                var icon = painterResource(id = R.drawable.ic_baseline_add_shopping_cart_24)
                var clicked by remember{mutableStateOf(false)}
                var clickCount by remember{mutableStateOf(0)}
                IconButton(onClick = {
                    clicked = true
                    clickCount++               //PlaceHolder //Where putting in ingredients to the shopping list on lRSS and SLSS
                    if(clickCount == 1 && navBackStackEntry.destination.route == LargeRecipeScreenSpec.route) {
                        viewModel.addIngredientsToStore()
                        Toast.makeText(context, "Added to Shopping List", Toast.LENGTH_SHORT).show()
                    }
                    else if(clickCount > 0 && navBackStackEntry.destination.route == ShoppingListScreenSpec.route) {
                        //I don't 100% no what to do here, I think its a search function????? - michael
                        Toast.makeText(context, "Added to Shopping List", Toast.LENGTH_SHORT).show()
                    }
                    if(clickCount > 1 && navBackStackEntry.destination.route != ShoppingListScreenSpec.route){
                        navController.navigate(ShoppingListScreenSpec.navigateTo())
                        {
                            launchSingleTop = true
                            popUpTo(ShoppingListScreenSpec.route)
                            clickCount = 0
                            clicked = false
                        }
                    }


                }) {
                    if(navBackStackEntry.destination.route == LargeRecipeScreenSpec.route) {
                        if (clicked) {
                            icon = painterResource(id = R.drawable.ic_baseline_shopping_cart_24)
                        } else {
                            icon = painterResource(id = R.drawable.ic_baseline_add_shopping_cart_24)
                        }
                    }
                    Icon(
                        painter = icon,
                        contentDescription = null
                    )
                }
            }
        }
    }

    fun navigateTo(vararg args: String?): String
}