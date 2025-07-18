package com.example.weatherapp.core.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ScreenWithLoadingContent(
    isLoadingContent: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    AnimatedContent(
        targetState = isLoadingContent,
        modifier = modifier.fillMaxSize()
    ) { isLoading ->
        if (isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                ContainedLoadingIndicator(
                    modifier = Modifier.size(dimensionResource(R.dimen.size_medium))
                )
            }
        } else {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenWithLoadingContentPreview() {
    WeatherAppTheme {
        ScreenWithLoadingContent(
            isLoadingContent = true,
        ) {
            Text(
                text = "test"
            )
        }
    }
}

