package ramseybros.WhatsForDinner.ui.screens

import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramseybros.WhatsForDinner.R



@Composable
private fun CustomLinearProgressBar(){
    Column(
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp)
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp),
            backgroundColor = Color.LightGray,
            color = colorResource(id = R.color.light_blue)
        )
    }
}




@Composable
fun LoadingScreen(onLoad : () -> Any) {
    Card(Modifier.clickable {onLoad()} ) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(50.dp))
        val image: Painter = painterResource(id = R.drawable.utensils)
        Text(fontWeight = FontWeight.ExtraBold, fontSize = 30.sp, color = colorResource(id = R.color.black), text = stringResource(id = R.string.app_name))
        Image(
            painter = image,
            contentDescription = "utensils",
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomLinearProgressBar()
    }
}
}

@Preview
@Composable
private fun LoadingScreenPreview(){
    LoadingScreen(){}
}
