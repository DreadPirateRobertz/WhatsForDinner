package ramseybros.WhatsForDinner.ui.navigation.specs

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.Constants
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel

sealed interface IScreenSpec {
    val route: String
    val arguments: List<String>
    val title: Int

    companion object {
        val start: String = LoadingScreenSpec.route
        val map = IScreenSpec::class.sealedSubclasses
            .associate { it.objectInstance?.route to it.objectInstance }




        @Composable
        fun TopBar(navController: NavHostController, navBackStackEntry: NavBackStackEntry?){
            val route = navBackStackEntry?.destination?.route ?: ""
            if (route != "") map[route]?.TopAppBarContent(navController = navController)
        }

        @Composable
        fun BottomBar(navController: NavHostController){
            BottomNavigation(
                backgroundColor = colorResource(id = R.color.purple_500)
            ) {
                // observe the backstack
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                // observe current route to change the icon
                // color,label color when navigated
                // observe current route to change the icon
                // color,label color when navigated
                val currentRoute = navBackStackEntry?.destination?.route
                // Bottom nav items we declared

                val fridge =Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_kitchen_24),
                    contentDescription = null
                )



                Constants.BottomNavItems.forEach { navItem ->

                    // Place the bottom nav items
                    BottomNavigationItem(

                        // it currentRoute is equal then its selected route
                        selected = currentRoute == navItem.route,

                        // navigate on click
                        onClick = {
                            navController.navigate(navItem.route)
                        },

                        // Icon of navItem

                        icon = {
                            if(navItem.label == "Kitchen") Icon(painter = painterResource(id = R.drawable.ic_baseline_kitchen_24), contentDescription = navItem.label)
                            else Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                        },

                        // label
                        label = {
                            Text(text = navItem.label)
                        },
                        alwaysShowLabel = false
                    )
                }
            }
        }


    }

    @Composable
    fun Content(viewModel: I_WhatsForDinnerViewModel, navController: NavHostController, backStackEntry: NavBackStackEntry)
    @Composable fun TopAppBarActions(navController: NavHostController)

    @Composable
    private fun TopAppBarContent(navController: NavHostController){

        TopAppBar(
            title =         //Title
            {
                Row(Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically) {
                    ProvideTextStyle(value = MaterialTheme.typography.h6) {
                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.high,
                        ){
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                ,
                                textAlign = TextAlign.Start,
                                maxLines = 1,
                                text = stringResource(id = title),
                                color = Color.Magenta
                            )
                        }
                    }
                }
            }
            ,
            backgroundColor = colorResource(id = R.color.purple_500),
            modifier = Modifier.fillMaxWidth(),
            navigationIcon = {
                if (navController.previousBackStackEntry != null) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack, //TODO: Customize Icon
                            contentDescription = "Back"
                        )
                    }
                } else null
            },
            actions = { TopAppBarActions(navController = navController) }
        )

        FloatingActionButton(onClick = { /*TODO*/ }) {

        }
    }

    fun navigateTo(vararg args: String?) : String

}