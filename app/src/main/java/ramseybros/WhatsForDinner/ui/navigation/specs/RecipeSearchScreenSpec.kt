package ramseybros.WhatsForDinner.ui.navigation.specs

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import kotlinx.coroutines.*
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.data.RecipeSearchModelState
import ramseybros.WhatsForDinner.ui.screens.RecipeSearchScreen
import ramseybros.WhatsForDinner.viewmodels.I_WhatsForDinnerViewModel

object RecipeSearchScreenSpec : IScreenSpec {
    override val route: String
        get() = "RecipeSearchScreen"
    override val arguments: List<NamedNavArgument>
        get() = listOf()
    override val title: Int = R.string.recipe_search_screen_title
    private val LOG_TAG = "ramseybros.RecipeSearchScreenSpec"


    @Composable
    override fun Content(
        viewModel: I_WhatsForDinnerViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry
    ) {
        val recipeLiveData = viewModel.getApiRecipeLiveData()
        val coroutineScope = rememberCoroutineScope()

        //ASYNC CODE
        //        fun onRequestList() {
//            coroutineScope.launch {
//                viewModel.RecipeSearchModelState.collect{
//                    val apiData = withContext(Dispatchers.IO) { viewModel.makeApiListRequest(it.searchText) }
//                    withContext(Dispatchers.IO) { viewModel.parseListJSON(apiData, viewModel) } //updates snapshotstatelist in viewModel, no need to return
//                }
//            }
//        }
        fun onRequestList() {
            coroutineScope.launch {
                var str: String = ""
                Log.d(LOG_TAG, "0 " + str)
                viewModel.RecipeSearchModelState.collect {
                    if (str == "") {
                        str = it.searchText
                        Log.d(LOG_TAG, "2 " + str)
                        val apiData =
                            withContext(Dispatchers.IO) { viewModel.makeApiListRequest(str) }
                        withContext(Dispatchers.IO) {
                            viewModel.parseListJSON(
                                apiData,
                                viewModel
                            )
                        } //updates snapshotstatelist in viewModel, no need to return
                    }
                }
            }
        }
        val recommendedRecipesList = viewModel.recommendedRecipeListLiveData.observeAsState().value
        viewModel.buildRecommendedRecipeList(recommendedRecipesList = recommendedRecipesList)
        RecipeSearchScreen(
            apiRecipeList = viewModel.getApiRecipeList(),
            onRequestRecipe = fun(recipe: Recipe) {
                coroutineScope.launch {
                    recipeLiveData.value = recipe
                    Log.d(
                        LOG_TAG,
                        "Calling navigateTo()"
                    )
                    withContext(Dispatchers.Main) {
                        navController.navigate(
                            LargeRecipeScreenSpec.navigateTo(
                                "search"
                            )
                        )
                    }
                }
            }
        )
    }


    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun TopAppBarActions(
        navController: NavHostController,
        viewModel: I_WhatsForDinnerViewModel
    ) {
//        val recipeSearchModelState by rememberFlowWithLifecycle(viewModel.RecipeSearchModelState)
//            .collectAsState(initial = RecipeSearchModelState.Empty)
        val x =
            viewModel.RecipeSearchModelState.collectAsState(initial = RecipeSearchModelState.Empty)
        //        }
        val coroutineScope = rememberCoroutineScope()
        fun onRequestList() {
            coroutineScope.launch {
                var str: String = ""
                Log.d(LOG_TAG, "0 " + str)
                viewModel.RecipeSearchModelState.collect {
                    if (str == "") {
                        str = it.searchText
                        Log.d(LOG_TAG, "2 " + str)
                        val apiData =
                            withContext(Dispatchers.IO) { viewModel.makeApiListRequest(str) }
                        withContext(Dispatchers.IO) {
                            viewModel.parseListJSON(
                                apiData,
                                viewModel
                            )
                        } //updates snapshotstatelist in viewModel, no need to return
                    }
                }
            }
        }
        SearchBar(
            searchText = x.value.searchText,
            stringResource(id = R.string.placeholder_search_bar),
            { viewModel.onSearchTextChanged(it) },
            { viewModel.onClearText() },
            {onRequestList()},
            {},
            viewModel)
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun SearchBar(
        searchText: String,
        placeholderText: String = "",
        onSearchTextChanged: (String) -> Unit = {},
        onClearClick: () -> Unit = {},
        onDone: () -> Unit,
        onNavigateBack: () -> Unit = {},
        viewModel: I_WhatsForDinnerViewModel
    ) {
        val context = LocalContext.current
        var showClearButton by remember { mutableStateOf(false) }
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusRequester = remember { FocusRequester() }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp)
                .onFocusChanged { focusState ->
                    showClearButton = (focusState.isFocused)
                }
                .focusRequester(focusRequester),
            value = searchText,
            onValueChange = onSearchTextChanged,
            placeholder = {
                Text(text = placeholderText, color = Color.White)
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.White,
                cursorColor = colorResource(id = R.color.teal_200),
                textColor = Color.White
            ),
            leadingIcon = {
                IconButton(onClick = { viewModel.askSpeechInput(context) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_mic_24),
                        contentDescription = null,
                        tint = colorResource(id = R.color.teal_200)
                    )
                }
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = showClearButton,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(onClick = { onClearClick() }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = null,
                            tint = colorResource(id = R.color.teal_200)
                        )
                    }

                }
            },
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    onDone()
                    keyboardController?.hide()
                    Log.d(LOG_TAG, "SEARCH DONE")
                },
            ),
        )

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }

    override fun navigateTo(vararg args: String?): String {
        return route
    }


}