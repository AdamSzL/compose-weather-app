package com.example.weatherapp.core.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import coil3.ColorImage
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import com.example.weatherapp.core.presentation.utils.weatherIconUrl
import com.example.weatherapp.ui.theme.WeatherAppTheme

@OptIn(ExperimentalCoilApi::class)
@Composable
fun WeatherIcon(
    icon: String,
    size: String,
    modifier: Modifier = Modifier
) {
    val previewHandler = AsyncImagePreviewHandler {
        ColorImage(Color.Red.toArgb())
    }
    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
        AsyncImage(
            model = weatherIconUrl.format(icon, size),
            contentDescription = null,
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalCoilApi::class)
@Preview(showBackground = true)
@Composable
private fun WeatherIconPreview() {
    WeatherAppTheme {
        WeatherIcon(
            icon = "10d",
            size = "2",
        )
    }
}

