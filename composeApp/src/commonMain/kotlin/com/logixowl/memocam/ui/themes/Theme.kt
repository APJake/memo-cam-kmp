package com.logixowl.memocam.ui.themes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val DarkColorPalette = darkColorScheme(
    primary = BrandWhite,
    primaryContainer = BrandBlackLight,
    onPrimary = BrandDeepBlack,
    secondary = BrandYellow,
    onSecondary = BlackInPurple,
    surface = BrandDeepBlack,
    onSurface = WhiteInYellow,
    background = BrandBlack,
    onBackground = WhiteInYellow
)

private val LightSBrandedColorPalette = lightColorScheme(
    primary = SBrandedBlack,
    primaryContainer = SBrandedBlack,
    secondary = BrandYellow,
    background = BrandPurpleWhite,
    surface = SBrandedWhite,
    onPrimary = SBrandedWhite,
    onSecondary = SBrandedWhiteHint,
    onSurface = SBrandedBlack,
    onBackground = SBrandedBlack
)

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightSBrandedColorPalette
    }

    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalRadius provides Radius(),
        LocalBrandColor provides BrandColor(darkTheme)
    ) {
        MaterialTheme(
            colorScheme = colors,
            typography = Typography,
            content = content
        )
    }
}
