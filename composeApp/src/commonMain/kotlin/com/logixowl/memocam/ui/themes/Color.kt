package com.logixowl.memocam.ui.themes

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

val SBrandedBlackHint = Color(0xFF35363C)
val SBrandedBlack = Color(0xff25262a)
val SBrandedWhiteHint = Color(0xFFA3A3A3)
val SBrandedWhite = Color(0xffffffff)
val SBrandedAccentBlue = Color(0xFF429FE6)
val SBrandedAccentOrange = Color(0xFFE9AC43)

val BrandPurpleWhite = Color(0xFFEEEDF5)

val BrandYellowLight = Color(0xFFFACF5E)
val BrandYellow = Color(0xffefba2d)

val BrandPurpleLight = Color(0xFF9486F7)
val BrandPurple = Color(0xFF655DA0)
val BrandPurpleDark = Color(0xFF403B6F)
val BrandPurpleTooDark = Color(0xFF302C4D)

val AccentColor = Color(0xFF2CD8BE)

val PurpleHint = Color(0xFFB3A9F9)
val PurpleHintPale = Color(0xFFC1BBEF)
val PurpleHintDark = Color(0xFF968ED4)

val WhiteInYellow = Color(0xFFF7F5EE)

val WhiteInPurple = Color(0xFFEEF1F7)
val BrandWhite = Color(0xFFF1F0EB)

val BlackInPurple = Color(0xFF6C6B7D)
val BrandBlackLight = Color(0xFF4A484E)
val BrandBlack = Color(0xFF1B1A1F)
val BrandDeepBlack = Color(0xFF121114)

val TextColor = Color(0xFF3F3E48)
val TextPaleColor = Color(0xFF6C6B7D)
val TextHintColor = Color(0xFFB1B0B8)

val SuccessColor = Color(0xFF08C281)
val DangerColor = Color(0xFFDF391C)

val BrandDataBlue = Color(0xFF1897AD)
val BrandDataPurple = Color(0xFF753ECE)
val BrandDataGreen = Color(0xFF16D119)
val BrandDataYellow = Color(0xFFDDDD0F)
val BrandDataRed = Color(0xFFCC391F)
val BrandDataOrange = Color(0xFFCC391F)
val BrandDataGrey = Color(0xFF747474)
val BrandDataBlack = Color(0xFF222121)

class BrandColor(val isDark: Boolean = false) {
    val error = Color(0xFFD85038)
    val success = Color(0xFF08C281)
    val warning = Color(0xFFDBC322)
}

val LocalBrandColor = compositionLocalOf { BrandColor() }

val MaterialTheme.brandColor: BrandColor
    @Composable
    @ReadOnlyComposable
    get() = LocalBrandColor.current
