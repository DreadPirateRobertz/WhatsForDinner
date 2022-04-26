package ramseybros.WhatsForDinner.data

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ramseybros.WhatsForDinner.R

private val ICON_SIZE = 24.dp
private val COLOR_NORMAL = Color(0xffEDEFF4)
private val COLOR_SELECTED = Color(0xff496DE2)
@Composable
fun AnimatableIcon(
    icon: Int,
    modifier: Modifier = Modifier,
    iconSize: Dp = ICON_SIZE,
    scale: Float = 1f,
    color: Color = COLOR_NORMAL,
    onClick: () -> Unit
) {
    // Animation params
    val animatedScale: Float by animateFloatAsState(
        targetValue = scale,
        // Here the animation spec serves no purpose but to demonstrate in slow speed.
        animationSpec = TweenSpec(
            durationMillis = 2000,
            easing = FastOutSlowInEasing
        )
    )
    val animatedColor by animateColorAsState(
        targetValue = color,
        animationSpec = TweenSpec(
            durationMillis = 2000,
            easing = FastOutSlowInEasing
        )
    )

    IconButton(
        onClick = onClick,
        modifier = modifier.size(iconSize)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "dummy",
            tint = animatedColor,
            modifier = modifier.scale(animatedScale)
        )
    }
}

@Preview
@Composable
fun PreviewIcon() {
    Surface {

        var selected by remember {
            mutableStateOf(false)
        }

        AnimatableIcon(
            icon = R.drawable.ic_baseline_home_24,
            scale = if (selected) 1.5f else 1f,
            color = if (selected) COLOR_SELECTED else COLOR_NORMAL,
        ) {
            selected = !selected
        }
    }
}