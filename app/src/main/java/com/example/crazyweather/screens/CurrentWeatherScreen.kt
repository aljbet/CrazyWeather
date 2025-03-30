package com.example.crazyweather.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.crazyweather.models.entities.CityWeather
import com.example.crazyweather.models.entities.WeatherMetrics
import com.example.crazyweather.models.vmmodels.CurrentWeatherState
import com.example.crazyweather.ui.theme.BorderBlue
import com.example.crazyweather.viewmodels.CurrentWeatherViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun CurrentWeatherScreen(
    cityName: String = "Саратов", // Наш любимый город!!!
    viewModel: CurrentWeatherViewModel
) {
    val weatherState by viewModel.weatherState.collectAsState()

    LaunchedEffect(cityName) {
        viewModel.loadWeather(cityName)
    }

    when (val state = weatherState) {
        is CurrentWeatherState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is CurrentWeatherState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Ошибка: ${state.message}", fontSize = 20.sp)
            }
        }
        is CurrentWeatherState.Success -> {
            WeatherContent(
                cityName = cityName,
                currentWeather = state.currentWeather,
            )
        }
    }
}

@Composable
private fun WeatherContent(
    cityName: String,
    currentWeather: WeatherMetrics,
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
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

        Text(
            text = "$cityName. Погода",
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(city) {
                start.linkTo(startGuideline)
                end.linkTo(endGuideline)
                top.linkTo(topGuideline)
                bottom.linkTo(hGuide7)
                width = Dimension.fillToConstraints
            }
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.constrainAs(temp) {
                start.linkTo(startGuideline)
                end.linkTo(guide25)
                top.linkTo(city.bottom)
                bottom.linkTo(hGuide20)
                width = Dimension.fillToConstraints
            }
        ) {
            Text(
                text = "${currentWeather.temperature?.toInt() ?: 0}°",
                fontSize = 50.sp
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.constrainAs(weatherIcon) {
                start.linkTo(temp.end)
                end.linkTo(guide55)
                top.linkTo(temp.top)
                bottom.linkTo(temp.bottom)
                width = Dimension.fillToConstraints
            }
        ) {
            Text(
                text = getWeatherIcon(currentWeather),
                fontSize = 80.sp
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.constrainAs(cityPicture) {
                start.linkTo(weatherIcon.end)
                end.linkTo(endGuideline)
                top.linkTo(temp.top)
                bottom.linkTo(hGuide30)
                width = Dimension.fillToConstraints
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.saratov),
                contentDescription = "$cityName. Фото",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            text = getWeatherDescription(currentWeather),
            fontSize = 23.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(weatherType) {
                start.linkTo(temp.start)
                end.linkTo(weatherIcon.end)
                top.linkTo(temp.bottom)
                bottom.linkTo(cityPicture.bottom)
                width = Dimension.fillToConstraints
            }
        )

        val guide45 = createGuidelineFromStart(0.45f)

        WeatherParam(
            name = "Скорость ветра",
            value = "${currentWeather.windSpeed?.toInt() ?: 0} м/с",
            modifier = Modifier.constrainAs(createRef()) {
                start.linkTo(startGuideline)
                end.linkTo(guide45)
                top.linkTo(cityPicture.bottom, margin = 50.dp)
                height = Dimension.value(140.dp)
                width = Dimension.fillToConstraints
            }
        )

        WeatherParam(
            name = "Влажность",
            value = "${currentWeather.humidity?.toInt() ?: 0}%",
            modifier = Modifier.constrainAs(createRef()) {
                start.linkTo(guide55)
                end.linkTo(endGuideline)
                top.linkTo(cityPicture.bottom, margin = 50.dp)
                height = Dimension.value(140.dp)
                width = Dimension.fillToConstraints
            }
        )

        WeatherParam(
            name = "Облачность",
            value = "${currentWeather.cloudiness?.toInt() ?: 0}%",
            modifier = Modifier.constrainAs(createRef()) {
                start.linkTo(startGuideline)
                end.linkTo(guide45)
                top.linkTo(cityPicture.bottom, margin = 220.dp)
                height = Dimension.value(140.dp)
                width = Dimension.fillToConstraints
            }
        )
    }
}

@Composable
private fun WeatherParam(name: String, value: String, modifier: Modifier = Modifier) {
    ConstraintLayout(modifier = modifier.border(2.dp, BorderBlue)) {
        val separator = createGuidelineFromTop(0.6f)
        val (nameRef, valueRef) = createRefs()

        Text(
            text = name,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .constrainAs(nameRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(separator)
                    width = Dimension.fillToConstraints
                }
                .padding(15.dp)
        )

        Text(
            text = value,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .constrainAs(valueRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(nameRef.bottom)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                }
        )
    }
}

private fun getWeatherDescription(metrics: WeatherMetrics): String {
    return when {
        (metrics.cloudiness ?: 0.0) > 70 -> "Облачно"
        (metrics.cloudiness ?: 0.0) > 30 -> "Переменная облачность"
        (metrics.temperature ?: 0.0) > 25 -> "Ясно и жарко"
        (metrics.temperature ?: 0.0) < 0 -> "Ясно и морозно"
        else -> "Ясно"
    }
}

private fun getWeatherIcon(metrics: WeatherMetrics): String {
    return when {
        (metrics.cloudiness ?: 0.0) > 70 -> "\u2601" // Облачно
        (metrics.cloudiness ?: 0.0) > 30 -> "\u26c5" // Переменная облачность
        (metrics.temperature ?: 0.0) < 0 -> "\u2744" // Снежинка
        else -> "\u2600" // Солнце
    }
}

@Preview
@Composable
fun CurrentWeatherScreenPreview() {
    CurrentWeatherScreen("Саратов", koinViewModel<CurrentWeatherViewModel>())
}

@Preview
@Composable
fun WeatherParamPreview() {
    WeatherParam("Параметр", "значение")
}