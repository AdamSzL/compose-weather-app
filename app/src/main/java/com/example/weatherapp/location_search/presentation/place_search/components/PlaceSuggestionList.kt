package com.example.weatherapp.location_search.presentation.place_search.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.location_search.domain.models.PlaceSuggestion
import com.example.weatherapp.location_search.presentation.place_search.fake.fakePlaceSuggestions
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun PlaceSuggestionList(
    placeSuggestions: List<PlaceSuggestion>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(placeSuggestions) { placeSuggestion ->
            ListItem(
                headlineContent = {
                    Text(
                        text = placeSuggestion.primaryText
                    )
                },
                supportingContent = {
                    placeSuggestion.secondaryText?.let {
                        Text(
                            text = it
                        )
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PlaceSuggestionListPreview() {
    WeatherAppTheme {
        PlaceSuggestionList(
            placeSuggestions = fakePlaceSuggestions
        )
    }
}

