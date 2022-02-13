package com.nikitakrapo.android.trips.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.nikitakrapo.android.trips.R

val robotoFamily = FontFamily(
    Font(R.font.roboto_thin, FontWeight.Thin),
    Font(R.font.roboto_thin_italic, FontWeight.Thin, FontStyle.Italic),

    Font(R.font.roboto_light, FontWeight.Light),
    Font(R.font.roboto_light_italic, FontWeight.Light, FontStyle.Italic),

    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_regular_italic, FontWeight.Normal, FontStyle.Italic),

    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_medium_italic, FontWeight.Medium, FontStyle.Italic),

    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_bold_italic, FontWeight.Bold, FontStyle.Italic),

    Font(R.font.roboto_black, FontWeight.Black),
    Font(R.font.roboto_black_italic, FontWeight.Black, FontStyle.Italic),
)

private fun robotoFont(
    fontSize: TextUnit,
    fontHeight: TextUnit,
    fontWeight: FontWeight = FontWeight.Normal,
    letterSpacing: TextUnit = 0.sp
) = TextStyle(
    fontFamily = robotoFamily,
    fontWeight = fontWeight,
    fontSize = fontSize,
    lineHeight = fontHeight,
    letterSpacing = letterSpacing
)

val TypographyM2 = androidx.compose.material.Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)

val Typography = Typography(
    displayLarge = robotoFont(57.sp, 64.sp),
    displayMedium = robotoFont(45.sp, 52.sp),
    displaySmall = robotoFont(36.sp, 44.sp),
    headlineLarge = robotoFont(32.sp, 40.sp),
    headlineMedium = robotoFont(28.sp, 36.sp),
    headlineSmall = robotoFont(24.sp, 32.sp),
    titleLarge = robotoFont(22.sp, 28.sp, FontWeight.Medium),
    titleMedium = robotoFont(16.sp, 24.sp, FontWeight.Medium, 0.15.sp),
    titleSmall = robotoFont(14.sp, 20.sp, FontWeight.Medium, 0.1.sp),
    bodyLarge = robotoFont(16.sp, 24.sp, FontWeight.Normal, 0.15.sp),
    bodyMedium = robotoFont(14.sp, 20.sp, FontWeight.Normal, 0.25.sp),
    bodySmall = robotoFont(12.sp, 16.sp, FontWeight.Normal, 0.25.sp),
    labelLarge = robotoFont(14.sp, 20.sp, FontWeight.Medium, 0.1.sp),
    labelMedium = robotoFont(12.sp, 16.sp, FontWeight.Medium, 0.5.sp),
    labelSmall = robotoFont(11.sp, 16.sp, FontWeight.Medium, 0.5.sp),
)