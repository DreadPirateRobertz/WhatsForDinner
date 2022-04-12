package ramseybros.WhatsForDinner.ui.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import ramseybros.WhatsForDinner.R
import ramseybros.WhatsForDinner.ui.navigation.specs.HomeScreenSpec

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    // AnimationEffect
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )
        delay(2000L)
        navController.popBackStack()
        navController.navigate(HomeScreenSpec.navigateTo())
    }

    // Image
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Cursive,
                fontSize = 40.sp,
                color = colorResource(id = R.color.black),
                text = stringResource(id = R.string.app_name)
            )
            Image(
                painter = painterResource(id = R.drawable.utensils_mod),
                contentDescription = "Logo",
                modifier = Modifier.scale(scale.value)
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomLinearProgressBar()
        }
    }
}

@Composable
private fun CustomLinearProgressBar(){
    Column(
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp)
    ) {
        LinearProgressIndicator(
            modifier = Modifier
//                .fillMaxWidth()
                .height(15.dp),
            backgroundColor = Color.LightGray,
            color = colorResource(id = R.color.light_blue)
        )
    }
}