package com.example.weatherapp.weather.presentation.weather.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun EditModeDropdownMenu(
    expanded: Boolean,
    isDeleteModeEnabled: Boolean,
    isAutoSaveEnabled: Boolean,
    isSavingLayout: Boolean,
    isUndoEnabled: Boolean,
    isRedoEnabled: Boolean,
    onExpandedToggle: (Boolean) -> Unit,
    onResetTiles: () -> Unit,
    onShuffleTiles: () -> Unit,
    onToggleAutoSave: (Boolean) -> Unit,
    onSaveLayout: () -> Unit,
    onUndoLayoutChange: () -> Unit,
    onRedoLayoutChange: () -> Unit,
    onToggleDeleteMode: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_medium))
    ) {
        IconButton(
            onClick = {
                onExpandedToggle(!expanded)
            }
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(R.string.open_edit_mode_options)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedToggle(false) }
        ) {
            EditModeDropdownMenuItem(
                title = R.string.reset_tiles,
                leadingIcon = R.drawable.ic_reset,
                onClick = onResetTiles,
            )

            EditModeDropdownMenuItem(
                title = R.string.shuffle_tiles,
                leadingIcon = R.drawable.ic_shuffle,
                onClick = onShuffleTiles,
            )

            AnimatedContent(
                targetState = isDeleteModeEnabled
            ) { isDeleting ->
                EditModeDropdownMenuItem(
                    title = if (isDeleting) R.string.exit_delete_mode else R.string.start_delete_mode,
                    leadingIcon = if (isDeleting) R.drawable.ic_close else R.drawable.ic_delete,
                    onClick = {
                        onToggleDeleteMode(!isDeleting)
                    },
                )
            }

            HorizontalDivider()

            EditModeDropdownMenuItem(
                title = R.string.auto_save,
                leadingIcon = R.drawable.ic_save,
                onClick = {
                    onToggleAutoSave(!isAutoSaveEnabled)
                },
                trailingIcon = {
                    Checkbox(
                        checked = isAutoSaveEnabled,
                        onCheckedChange = {
                            onToggleAutoSave(it)
                        }
                    )
                }
            )

            AnimatedVisibility(
                visible = !isAutoSaveEnabled,
                enter = expandVertically(
                    expandFrom = Alignment.Top
                ),
                exit = shrinkVertically(
                    shrinkTowards = Alignment.Top
                )
            ) {
                EditModeDropdownMenuItem(
                    title = R.string.save_layout,
                    leadingIcon = R.drawable.ic_check,
                    onClick = onSaveLayout,
                    enabled = !isSavingLayout,
                )
            }

            EditModeDropdownMenuItem(
                title = R.string.undo_layout_change,
                leadingIcon = R.drawable.ic_undo,
                onClick = onUndoLayoutChange,
                enabled = isUndoEnabled,
            )

            EditModeDropdownMenuItem(
                title = R.string.redo_layout_change,
                leadingIcon = R.drawable.ic_redo,
                onClick = onRedoLayoutChange,
                enabled = isRedoEnabled
            )

        }
    }
}

@Composable
private fun EditModeDropdownMenuItem(
    @StringRes title: Int,
    @DrawableRes leadingIcon: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    DropdownMenuItem(
        text = {
            Text(
                text = stringResource(title)
            )
        },
        trailingIcon = if (trailingIcon != null) {
            {
                trailingIcon()
            }
        } else null,
        leadingIcon = {
            Icon(
                painter = painterResource(leadingIcon),
                contentDescription = null
            )
        },
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
    )
}

class ExpandedParameterProvider: PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(true, false)
}

@Preview(showBackground = true)
@Composable
private fun EditingModeDropdownMenuPreview(
    @PreviewParameter(ExpandedParameterProvider::class) expanded: Boolean
) {
    WeatherAppTheme {
        EditModeDropdownMenu(
            expanded = expanded,
            isDeleteModeEnabled = true,
            isAutoSaveEnabled = true,
            isSavingLayout = false,
            isUndoEnabled = true,
            isRedoEnabled = true,
            onExpandedToggle = {},
            onResetTiles = {},
            onShuffleTiles = {},
            onToggleAutoSave = {},
            onSaveLayout = {},
            onUndoLayoutChange = {},
            onRedoLayoutChange = {},
            onToggleDeleteMode = {}
        )
    }
}