package ramseybros.WhatsForDinner.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import ramseybros.WhatsForDinner.util.CharacterWorker
import ramseybros.WhatsForDinner.viewmodels.WhatsForDinnerViewModel

@Composable
fun RecipeSearchScreen() {
    Text(
        textAlign = TextAlign.Center,
        text = "Recipe Search",
        fontSize = 24.sp
    )

}

@Preview
@Composable
private fun Preview() {
    RecipeSearchScreen()
}