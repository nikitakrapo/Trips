package com.nikitakrapo.android.trips.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val lightColorScheme = lightColorScheme(
    primary = Primary40,
    onPrimary = Color.White,
    primaryContainer = Primary90,
    onPrimaryContainer = Primary10,
    inversePrimary = Primary40.inverted(),
    secondary = Secondary40,
    onSecondary = Color.White,
    secondaryContainer = Secondary90,
    onSecondaryContainer = Secondary10,
    tertiary = Tertiary40,
    onTertiary = Color.White,
    tertiaryContainer = Tertiary90,
    onTertiaryContainer = Tertiary10,
    background = Neutral99,
    onBackground = Neutral10,
    surface = Neutral99,
    onSurface = Neutral10,
    surfaceVariant = NeutralVariant90,
    onSurfaceVariant = NeutralVariant30,
    inverseSurface = Neutral99.inverted(),
    inverseOnSurface = Neutral10.inverted(),
    error = Error40,
    onError = Color.White,
    errorContainer = Error90,
    onErrorContainer = Error10,
    outline = NeutralVariant50
)

val lightColors = lightColors(
    primary = lightColorScheme.primary,
    primaryVariant = lightColorScheme.primaryContainer,
    secondary = lightColorScheme.secondary,
    secondaryVariant = lightColorScheme.secondaryContainer,
    background = lightColorScheme.background,
    surface = lightColorScheme.surface,
    error = lightColorScheme.error,
    onPrimary = lightColorScheme.onPrimary,
    onSecondary = lightColorScheme.onSecondary,
    onBackground = lightColorScheme.onBackground,
    onSurface = lightColorScheme.onSurface,
    onError = lightColorScheme.onError,
)

private val darkColorScheme = darkColorScheme(
    primary = Primary80,
    onPrimary = Primary20,
    primaryContainer = Primary30,
    onPrimaryContainer = Primary90,
    inversePrimary = Primary80.inverted(),
    secondary = Secondary80,
    onSecondary = Secondary20,
    secondaryContainer = Secondary30,
    onSecondaryContainer = Secondary90,
    tertiary = Tertiary80,
    onTertiary = Tertiary20,
    tertiaryContainer = Tertiary30,
    onTertiaryContainer = Tertiary90,
    background = Neutral10,
    onBackground = Neutral90,
    surface = Neutral10,
    onSurface = Neutral80,
    surfaceVariant = NeutralVariant30,
    onSurfaceVariant = NeutralVariant80,
    inverseSurface = Neutral10.inverted(),
    inverseOnSurface = NeutralVariant80.inverted(),
    error = Error80,
    onError = Error20,
    errorContainer = Error30,
    onErrorContainer = Error90,
    outline = NeutralVariant60
)

val darkColors = darkColors(
    primary = darkColorScheme.primary,
    primaryVariant = darkColorScheme.primaryContainer,
    secondary = darkColorScheme.secondary,
    secondaryVariant = darkColorScheme.secondaryContainer,
    background = darkColorScheme.background,
    surface = darkColorScheme.surface,
    error = darkColorScheme.error,
    onPrimary = darkColorScheme.onPrimary,
    onSecondary = darkColorScheme.onSecondary,
    onBackground = darkColorScheme.onBackground,
    onSurface = darkColorScheme.onSurface,
    onError = darkColorScheme.onError,
)

fun Color.inverted(): Color {
    val maxValue = this.colorSpace.getMaxValue(0)
    return Color(
        red = maxValue - this.red,
        green = maxValue - this.green,
        blue = maxValue - this.blue,
        alpha = this.alpha
    )
}

@Composable
fun TripsTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    //FIXME: move to m3 fully
    val colorScheme = if (darkTheme) {
        darkColorScheme
    } else {
        lightColorScheme
    }

    val colors = if (darkTheme) {
        darkColors
    } else {
        lightColors
    }

    androidx.compose.material.MaterialTheme(
        colors = colors,
        typography = TypographyM2,
        shapes = Shapes
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}