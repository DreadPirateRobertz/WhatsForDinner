package ramseybros.WhatsForDinner.ui.navigation.specs

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.NavigationItem
import ramseybros.WhatsForDinner.ui.theme.*
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
        fun BottomBar(
            navController: NavHostController,
            navBackStackEntry: NavBackStackEntry?,
            viewModel: I_WhatsForDinnerViewModel
        ) {
            val route = navBackStackEntry?.destination?.route ?: ""
            if (route != "") allScreens[route]?.BottomAppBarContent(
                navController = navController,
                navBackStackEntry,
                viewModel
            )
        }

        @Composable
        fun FAB(
            navController: NavHostController,
            navBackStackEntry: NavBackStackEntry?,
            viewModel: I_WhatsForDinnerViewModel
        ) {
            val route = navBackStackEntry?.destination?.route ?: ""
            if (route != "") allScreens[route]?.FAB_Content(
                navController = navController,
                navBackStackEntry,
                viewModel = viewModel
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
                            Column {
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
                CornerSize(percent = 50)
            ),
            backgroundColor = bgColor
        ) {
            Row(
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {


                BottomNavigation(
                    backgroundColor = bgColor,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val items = listOf(
                        NavigationItem.SavedRecipes,
                        NavigationItem.RecommendedRecipes,
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
                                    if (navItem.route == HomeScreenSpec.route) viewModel.onHomeFlag =
                                        true
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


                                    Icon(
                                        painter = painterResource(id = navItem.icon),
                                        contentDescription = stringResource(id = navItem.title)
                                    )

                                    Spacer(Modifier.weight(.1f))
                                },
                                label = {
                                    val configuration = LocalConfiguration.current
                                    when (configuration.orientation) {
                                        Configuration.ORIENTATION_PORTRAIT -> {
                                            if (navItem.title == R.string.nav_title_recommended) navItem.title =
                                                R.string.nav_title_recommended_abbreviated
                                        }
                                        Configuration.ORIENTATION_LANDSCAPE -> {
                                            if (navItem.title == R.string.nav_title_recommended_abbreviated) navItem.title =
                                                R.string.nav_title_recommended
                                        }
                                    }


                                    Text(
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 12.sp,
                                        text = stringResource(id = navItem.title)
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

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun FAB_Content(
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?,
        viewModel: I_WhatsForDinnerViewModel
    ) {
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
                var clicked by remember { mutableStateOf(false) }
                var clickCount by remember { mutableStateOf(0) }
                var textExpanded by remember { mutableStateOf(false) }
                var addText by remember { mutableStateOf("") }
                var textList = remember { mutableStateListOf<String>() }
                IconButton(onClick = {
                    clicked = true
                    clickCount++
                    if (clickCount == 1 && navBackStackEntry.destination.route == LargeRecipeScreenSpec.route) {
                        viewModel.addIngredientsToStore()
                        Toast.makeText(context, "Added to Shopping List", Toast.LENGTH_SHORT).show()
                    } else if (clickCount > 0 && navBackStackEntry.destination.route == ShoppingListScreenSpec.route) {
                        Log.d("recipe", addText)
                        textExpanded = if (textExpanded && addText != "") {
                            viewModel.setIngredientsToAdd(addText)
                            viewModel.addIngredientsToStore()
                            Toast.makeText(context, "Added to Shopping List", Toast.LENGTH_SHORT)
                                .show()
                            false
                        } else true
                    }
                    if (clickCount > 1 && navBackStackEntry.destination.route != ShoppingListScreenSpec.route) {
                        navController.navigate(ShoppingListScreenSpec.navigateTo()) {
                            launchSingleTop = true
                            popUpTo(ShoppingListScreenSpec.route)
                            clickCount = 0
                            clicked = false
                        }
                    }


                }) {
                    if (navBackStackEntry.destination.route == LargeRecipeScreenSpec.route) {
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

                fun onSubmit() {
                    textList.forEach {
                        viewModel.setIngredientsToAdd(it)
                        viewModel.addIngredientsToStore()
                    }
                    textExpanded = false
                    Toast.makeText(
                        context,
                        "Added to Shopping List",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                fun onClick() {
                    if (addText != "") {
                        textList.add(addText)
                    }
                }

                var colors = listOf(
                    colorResource(id = R.color.white),
                    colorLightPrimary,
                    colorLightSecondary,
                    Color.DarkGray
                )
                if (isSystemInDarkTheme()) colors =
                    listOf(colorDarkBackground, colorDarkPrimary, colorDarkSecondary, Color.White)
                if (textExpanded) {
                    Popup(
                        alignment = Alignment.BottomCenter,
                        offset = IntOffset(0, 0),
                        onDismissRequest = { textExpanded = false },
                        properties = PopupProperties(focusable = true),
                        content = {
                            Column(
                                Modifier
                                    .fillMaxHeight(0.75f)
                                    .fillMaxWidth(0.90f)
                                    .border(
                                        2.dp,
                                        colors[2],
                                        RoundedCornerShape(8.dp)
                                    )
                                    .background(
                                        color = colors[0],
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(8.dp)
                            )
                            {
                                OutlinedTextField(
                                    value = addText,
                                    onValueChange = {
                                        if (it.contains('\n')) {
                                            onClick()
                                            addText = ""
                                        } else {
                                            addText = it
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(colors[0]),
                                    label = { Text(text = stringResource(id = R.string.add_ingredient_to_cart_label)) },
                                    trailingIcon = {
                                        IconButton(onClick = { onClick() }) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_baseline_add_circle_regular_size),
                                                contentDescription = null,
                                                tint = colorResource(id = R.color.teal_200)
                                            )
                                        }
                                    },
                                    colors = TextFieldDefaults.textFieldColors(
                                        focusedIndicatorColor = colors[3],
                                        unfocusedIndicatorColor = Color.Transparent,
                                        backgroundColor = colors[3],
                                        cursorColor = colorResource(id = R.color.teal_200),
                                        textColor = colors[3]
                                    ),
                                )
                                LazyVerticalGrid(
                                    cells = GridCells.Fixed(2),
                                    contentPadding = PaddingValues(7.dp),
                                    modifier = Modifier.weight(0.8f)
                                ) {
                                    items(textList.size) { index ->
                                        Box(
                                            Modifier
                                                .padding(4.dp)
                                                .border(
                                                    2.dp,
                                                    colors[3],
                                                    shape = RoundedCornerShape(5)
                                                )
                                                .clickable {
                                                    textList.remove(textList[index])
                                                }
                                                .height(72.dp)) {
                                            Text(
                                                text = textList[index],
                                                fontSize = 16.sp,
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier.padding(4.dp),
                                                color = colors[3]
                                            )
                                        }
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .weight(0.1f)
                                        .fillMaxWidth()
                                ) {
                                    IconButton(
                                        modifier = Modifier.fillMaxWidth(),
                                        onClick = { onSubmit() }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_baseline_add_shopping_cart_24),
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    fun navigateTo(vararg args: String?): String
}