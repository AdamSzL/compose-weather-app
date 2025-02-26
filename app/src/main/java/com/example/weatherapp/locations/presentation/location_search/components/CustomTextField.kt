package com.example.weatherapp.locations.presentation.location_search.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    singleLine: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focusRequester = remember { FocusRequester() }

    val colors = OutlinedTextFieldDefaults.colors(
        unfocusedBorderColor = Color.Transparent,
        focusedBorderColor = Color.Transparent,
    )
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.focusRequester(focusRequester),
        interactionSource = interactionSource,
        enabled = enabled,
        singleLine = singleLine,
    ) {
        OutlinedTextFieldDefaults.DecorationBox(
            value = value,
            visualTransformation = VisualTransformation.None,
            innerTextField = it,
            singleLine = singleLine,
            enabled = enabled,
            interactionSource = interactionSource,
            contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                start = 0.dp,
                end = 0.dp,
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.search_for_location)
                )
            },
            colors = colors
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomTextFieldPreview() {
    WeatherAppTheme {
        CustomTextField(
            value = "123",
            onValueChange = {},
        )
    }
}

