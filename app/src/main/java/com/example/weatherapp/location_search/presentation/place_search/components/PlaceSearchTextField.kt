package com.example.weatherapp.location_search.presentation.place_search.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.location_search.presentation.place_search.LocationSearchScreenEvent
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun PlaceSearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onLeadingIconClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val textFieldColors = TextFieldDefaults.colors(
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
        focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
    )

    TextField(
        leadingIcon = {
            IconButton(
                onClick = onLeadingIconClick,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.navigate_back)
                )
            }
        },
        placeholder = {
            Text(
                text = stringResource(R.string.search_for_city_or_place)
            )
        },
        textStyle = MaterialTheme.typography.bodyLarge,
        colors = textFieldColors,
        value = value,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchClick()
            },
        ),
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
private fun PlaceSearchTextFieldPreview() {
    WeatherAppTheme {
        PlaceSearchTextField(
            value = "Krakow",
            onValueChange = {},
            onLeadingIconClick = {},
            onSearchClick = {}
        )
    }
}

