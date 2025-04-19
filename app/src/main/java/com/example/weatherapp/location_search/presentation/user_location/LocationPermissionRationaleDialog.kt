package com.example.weatherapp.location_search.presentation.user_location

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.core.presentation.components.ConfirmAlertDialog
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun LocationPermissionRationaleDialog(
    onDialogDismiss: () -> Unit,
    onPermissionDialogLaunch: () -> Unit,
    modifier: Modifier = Modifier
) {
    ConfirmAlertDialog(
        title = stringResource(R.string.location_permission_required),
        text = stringResource(R.string.location_permission_rationale_text),
        icon = Icons.Default.LocationOn,
        onDismissRequest = onDialogDismiss,
        onConfirmation = onPermissionDialogLaunch,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun LocationPermissionRationalePreview() {
    WeatherAppTheme {
        LocationPermissionRationaleDialog(
            onDialogDismiss = {},
            onPermissionDialogLaunch = {}
        )
    }
}

