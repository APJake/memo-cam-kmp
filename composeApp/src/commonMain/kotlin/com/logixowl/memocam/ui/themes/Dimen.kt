package com.logixowl.memocam.ui.themes

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val appBarHeight = 75.dp

class Spacing {
    val default: Dp = 0.dp
    val extraSmall = 4.dp
    val small = 8.dp
    val small2 = 12.dp
    val medium = 16.dp
    val medium2 = 20.dp
    val medium3 = 26.dp
    val large = 32.dp
    val extraLarge = 64.dp
}

object AppDimens {
    val settingItemHeight = 56.dp
}

object IconSize {
    val extraSmall = 12.dp
    val small = 18.dp
    val medium = 24.dp
    val medium1 = 28.dp
    val large = 40.dp
    val extraLarge = 80.dp
}

object AppElevation {
    val small = 3.dp
    val medium = 10.dp
    val large = 20.dp
}

class Radius {
    val small = 4.dp
    val medium = 12.dp
    val large = 25.dp
}

val LocalSpacing = compositionLocalOf { Spacing() }
val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current

val LocalRadius = compositionLocalOf { Radius() }
val MaterialTheme.radius: Radius
    @Composable
    @ReadOnlyComposable
    get() = LocalRadius.current
