package com.example.weatherapp.locations.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImagePainter.State.Empty.painter
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun LocationTypeBlock(
    visible: Boolean,
    @DrawableRes icon: Int,
    @StringRes text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = visible,
        enter = expandVertically(
            expandFrom = Alignment.Top,
        ) + fadeIn(),
        exit = shrinkVertically(
            shrinkTowards = Alignment.Bottom,
        ) + fadeOut(),
        modifier = modifier
    ) {
        FloatingActionButton(
            onClick = onClick,
            modifier = Modifier.clip(RoundedCornerShape(dimensionResource(R.dimen.size_small)))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(R.dimen.padding_big),
                    vertical = dimensionResource(R.dimen.padding_medium)
                )
            ) {
                Text(
                    text = stringResource(text),
                )
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                Icon(
                    painter = painterResource(icon),
                    contentDescription = stringResource(text),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LocationTypeBlockPreview() {
    WeatherAppTheme {
        LocationTypeBlock(
            visible = true,
            icon = R.drawable.ic_map,
            text = R.string.pick_from_map,
            onClick = {}
        )
    }
}

