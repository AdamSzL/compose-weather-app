package com.example.weatherapp.location_list.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleFloatingActionButton
import androidx.compose.material3.ToggleFloatingActionButtonDefaults.animateIcon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AddLocationFloatingActionButtonMenu(
    isMenuExpanded: Boolean,
    onExpandedToggle: () -> Unit,
    onFetchUserLocation: () -> Unit,
    onNavigateToLocationMap: () -> Unit,
    onNavigateToLocationSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    val toggleButtonContentDescription = stringResource(R.string.toggle_add_location_menu)
    val expandedText = stringResource(R.string.expanded)
    val collapsedText = stringResource(R.string.collapsed)

    val fabMenuItems: List<Triple<Int, Int, () -> Unit>> = listOf(
        Triple(R.drawable.ic_my_location, R.string.my_location, onFetchUserLocation),
        Triple(R.drawable.ic_map, R.string.pick_from_map, onNavigateToLocationMap),
        Triple(R.drawable.ic_search, R.string.search_for_location, onNavigateToLocationSearch),
    )

    FloatingActionButtonMenu(
        expanded = isMenuExpanded,
        button = {
            ToggleFloatingActionButton(
                modifier =
                    Modifier.semantics {
                        traversalIndex = -1f
                        stateDescription = if (isMenuExpanded) expandedText else collapsedText
                        contentDescription = toggleButtonContentDescription
                    },
                checked = isMenuExpanded,
                onCheckedChange = { onExpandedToggle() },
            ) {
                val imageVector by remember {
                    derivedStateOf {
                        if (checkedProgress > 0.5f) Icons.Filled.Close else Icons.Filled.Add
                    }
                }
                Icon(
                    painter = rememberVectorPainter(imageVector),
                    contentDescription = null,
                    modifier = Modifier.animateIcon({ checkedProgress })
                )
            }
        },
        modifier = modifier
    ) {
        fabMenuItems.forEachIndexed { index, item ->
            val (icon, text, action) = item
            FloatingActionButtonMenuItem(
                onClick = {
                    onExpandedToggle()
                    action()
                } ,
                icon = {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = null
                    )
                },
                text = {
                    Text(
                        text = stringResource(text),
                    )
                }
            )
        }
    }
}

class IsMenuExpandedParameterProvider: PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(true, false)
}

@Preview(showBackground = true)
@Composable
private fun AddLocationFloatingActionButtonMenuPreview(
    @PreviewParameter(IsMenuExpandedParameterProvider::class) isMenuExpanded: Boolean
) {
    WeatherAppTheme {
        AddLocationFloatingActionButtonMenu(
            isMenuExpanded = isMenuExpanded,
            onExpandedToggle = {},
            onFetchUserLocation = {},
            onNavigateToLocationMap = {},
            onNavigateToLocationSearch = {}
        )
    }
}
