package ramseybros.WhatsForDinner.ui.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
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
            targetValue = 1.25f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(10f).getInterpolation(it)
                },)
        )
        delay(2500L)
        navController.popBackStack()
        navController.navigate(HomeScreenSpec.navigateTo())
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        val infiniteTransition = rememberInfiniteTransition()

        val scale by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1.4f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 600,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            drawCircle(
                color = Color(0xFF03DAC5),
                center = Offset(
                    x = canvasWidth / 2,
                    y = canvasHeight / 2 + 60
                ),
                radius = size.minDimension / 4f,
            )
            drawCircle(
                color = Color(0xFF000000),
                center = Offset(
                    x = canvasWidth / 2,
                    y = canvasHeight / 2 + 60
                ),
                radius = size.minDimension / 6f,
            )
            drawCircle(
                color = Color(0xFF03DAC5),
                center = Offset(
                    x = canvasWidth / 2,
                    y = canvasHeight / 2 + 60
                ),
                radius = size.minDimension / 16f,
            )
        }
    }

    // Image
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if(!isSystemInDarkTheme()) {
                Text(
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Cursive,
                    fontSize = 40.sp,
                    color = colorResource(id = R.color.black),
                    text = stringResource(id = R.string.app_name)
                )
            } else {
                Text(
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Cursive,
                    fontSize = 40.sp,
                    color = colorResource(id = R.color.white),
                    text = stringResource(id = R.string.app_name)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.utensils_mod),
                contentDescription = "Logo",
                modifier = Modifier.scale(scale.value)
            )
//            Spacer(modifier = Modifier.height(16.dp))
//            CustomLinearProgressBar()
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
            color = colorResource(id = R.color.teal_700)
        )
    }
}