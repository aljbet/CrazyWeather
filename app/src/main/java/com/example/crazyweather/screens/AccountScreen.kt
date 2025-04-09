package com.example.crazyweather.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.crazyweather.R
import com.example.crazyweather.ui.theme.BorderBlue
import com.example.crazyweather.ui.theme.CrazyWeatherTheme

@Composable
fun AccountScreen(navController: NavController) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val startGuideline = createGuidelineFromStart(0.03f)
        val endGuideline = createGuidelineFromEnd(0.03f)
        val topGuideline = createGuidelineFromTop(0.03f)
        val bottomGuideline = createGuidelineFromBottom(0.03f)

        val hGuide25 = createGuidelineFromTop(0.25f)

        val img = createRef()
        val name = createRef()

        Box(contentAlignment = Alignment.Center, modifier = Modifier.constrainAs(img) {
            start.linkTo(startGuideline)
            end.linkTo(endGuideline)
            top.linkTo(topGuideline)
            bottom.linkTo(hGuide25)
            height = Dimension.fillToConstraints
            width = Dimension.fillToConstraints
        }.fillMaxSize()) {
            Image(painter = painterResource(id = R.drawable.hedgehog),
                "Фото",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxHeight().clip(RoundedCornerShape(16.dp)))
        }

        Text("Где ёж", fontSize = 35.sp, textAlign = TextAlign.Center, modifier = Modifier.constrainAs(name) {
            start.linkTo(startGuideline)
            end.linkTo(endGuideline)
            top.linkTo(img.bottom)
            width = Dimension.fillToConstraints
        }.fillMaxWidth())

        val favoriteHeader = createRef()
        Text("Любимые города:", fontSize = 20.sp, modifier = Modifier.constrainAs(favoriteHeader) {
            start.linkTo(startGuideline)
            end.linkTo(endGuideline)
            top.linkTo(name.bottom, margin = 20.dp)
            width = Dimension.fillToConstraints
        }.fillMaxWidth())

        val favoriteCity = createRef()
        FavoriteCity("Саратов", modifier = Modifier.constrainAs(favoriteCity) {
            start.linkTo(startGuideline)
            end.linkTo(endGuideline)
            top.linkTo(favoriteHeader.bottom, margin = 10.dp)
            width = Dimension.fillToConstraints
        }.fillMaxWidth())

        val favoriteCity2 = createRef()
        FavoriteCity("Сургут", modifier = Modifier.constrainAs(favoriteCity2) {
            start.linkTo(startGuideline)
            end.linkTo(endGuideline)
            top.linkTo(favoriteCity.bottom, margin = 10.dp)
            width = Dimension.fillToConstraints
        }.fillMaxWidth())

        val favoriteCity3 = createRef()
        FavoriteCity("Якутск", modifier = Modifier.constrainAs(favoriteCity3) {
            start.linkTo(startGuideline)
            end.linkTo(endGuideline)
            top.linkTo(favoriteCity2.bottom, margin = 10.dp)
            width = Dimension.fillToConstraints
        }.fillMaxWidth())

        val history = createRef()
        Box(
            modifier = Modifier.constrainAs(history){
                start.linkTo(startGuideline)
                end.linkTo(endGuideline)
                bottom.linkTo(bottomGuideline)
                width = Dimension.fillToConstraints
            }.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                navController.navigate(Screen.History.route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }) {
                Text("История")
            }

        }
    }
}

@Composable
fun FavoriteCity(name: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth().border(2.dp, BorderBlue)) {
        Text(name,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(0.dp))
    }
}

@Preview
@Composable
fun AccountScreenPreview() {
    CrazyWeatherTheme {
        AccountScreen(rememberNavController())
    }
}

@Preview
@Composable
fun FavoriteCityPreview() {
    FavoriteCity("Саратов")
}