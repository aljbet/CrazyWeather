package com.example.crazyweather.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.crazyweather.R
import com.example.crazyweather.ui.theme.BorderBlue


@Composable
fun CurrentWeatherScreen(cityName: String = "Саратов") {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val startGuideline = createGuidelineFromStart(0.03f)
        val endGuideline = createGuidelineFromEnd(0.03f)
        val topGuideline = createGuidelineFromTop(0.03f)
        val bottomGuideline = createGuidelineFromBottom(0.03f)

        val guide25 = createGuidelineFromStart(0.25f)
        val guide55 = createGuidelineFromStart(0.55f)

        val hGuide7 = createGuidelineFromTop(0.1f)
        val hGuide20 = createGuidelineFromTop(0.2f)
        val hGuide30 = createGuidelineFromTop(0.3f)

        val city = createRef()
        val temp = createRef()
        val weatherIcon = createRef()
        val cityPicture = createRef()
        val weatherType = createRef()

        Text("${cityName}. Погода", fontSize = 28.sp, textAlign = TextAlign.Center, modifier = Modifier.constrainAs(city) {
            start.linkTo(startGuideline)
            end.linkTo(endGuideline)
            top.linkTo(topGuideline)
            bottom.linkTo(hGuide7)
            height = Dimension.fillToConstraints
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }.fillMaxSize())

        Box(contentAlignment = Alignment.Center, modifier = Modifier.constrainAs(temp) {
            start.linkTo(startGuideline)
            end.linkTo(guide25)
            top.linkTo(city.bottom)
            bottom.linkTo(hGuide20)
            height = Dimension.fillToConstraints
            width = Dimension.fillToConstraints
        }.fillMaxSize()) {
            Text("+3°", fontSize = 50.sp)
        }

        Box(contentAlignment = Alignment.Center, modifier = Modifier.constrainAs(weatherIcon) {
            start.linkTo(temp.end)
            end.linkTo(guide55)
            top.linkTo(temp.top)
            bottom.linkTo(temp.bottom)
            height = Dimension.fillToConstraints
            width = Dimension.fillToConstraints
        }.fillMaxSize()) {
            Text("\u26c5", fontSize = 80.sp)
        }

        Box(contentAlignment = Alignment.Center, modifier = Modifier.constrainAs(cityPicture) {
            start.linkTo(weatherIcon.end)
            end.linkTo(endGuideline)
            top.linkTo(temp.top)
            bottom.linkTo(hGuide30)
            height = Dimension.fillToConstraints
            width = Dimension.fillToConstraints
        }.fillMaxSize()) {
            Image(painter = painterResource(id = R.drawable.saratov),
                "${cityName}. Фото",
                contentScale = ContentScale.Crop)
        }

        Text("Переменная облачность", fontSize = 23.sp, textAlign = TextAlign.Center, modifier = Modifier.constrainAs(weatherType) {
            start.linkTo(temp.start)
            end.linkTo(weatherIcon.end)
            top.linkTo(temp.bottom)
            bottom.linkTo(cityPicture.bottom)
            height = Dimension.fillToConstraints
            width = Dimension.fillToConstraints
        }.fillMaxSize())


        val guide45 = createGuidelineFromStart(0.45f)

        val wind = createRef()
        val modifier = Modifier.constrainAs(wind) {
            start.linkTo(startGuideline)
            end.linkTo(guide45)
            top.linkTo(cityPicture.bottom, margin = 50.dp)
            height = Dimension.value(140.dp)
            width = Dimension.fillToConstraints
        }
        WeatherParam("скорость ветра", "10 м/с", modifier)

        val humidity = createRef()
        val humidityModifier = Modifier.constrainAs(humidity) {
            start.linkTo(guide55)
            end.linkTo(endGuideline)
            top.linkTo(cityPicture.bottom, margin = 50.dp)
            height = Dimension.value(140.dp)
            width = Dimension.fillToConstraints
        }
        WeatherParam("влажность", "71%", humidityModifier)

        val cloudy = createRef()
        val cloudyModifier = Modifier.constrainAs(cloudy) {
            start.linkTo(startGuideline)
            end.linkTo(guide45)
            top.linkTo(wind.bottom, margin = 50.dp)
            height = Dimension.value(140.dp)
            width = Dimension.fillToConstraints
        }
        WeatherParam("облачность", "50%", cloudyModifier)
    }
}

@Composable
fun WeatherParam(name: String, value: String, modifier: Modifier = Modifier) {
    ConstraintLayout(modifier = modifier.fillMaxSize().border(2.dp, BorderBlue)) {
        val separator = createGuidelineFromTop(0.6f)

        val (nameRef, valueRef) = createRefs()

        Text(name, fontSize = 20.sp, textAlign = TextAlign.Center, modifier = Modifier.constrainAs(nameRef) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(separator)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }.fillMaxSize().padding(15.dp))

        Text(value, fontSize = 20.sp, textAlign = TextAlign.Center, modifier = Modifier.constrainAs(valueRef) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(nameRef.bottom)
            bottom.linkTo(parent.bottom)
            height = Dimension.fillToConstraints
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }.fillMaxSize())
    }
}

@Preview
@Composable
fun CurrentWeatherScreenPreview() {
    CurrentWeatherScreen()
}

@Preview
@Composable
fun WeatherParamPreview() {
    WeatherParam("Параметр", "значение")
}