package ramseybros.WhatsForDinner.ui.navigation.specs

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import ramseybros.WhatsForDinner.R
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
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                text = stringResource(id = title),
                                color = Color.Magenta
                            )
                        }
                    }
                }
            }
            ,
            backgroundColor = colorResource(id = R.color.light_blue),
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
    }
    fun navigateTo(vararg args: String?) : String

}