package xyz.deliverease.deliverease.android.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val DelivereaseTypography = Typography(
    titleMedium = Typography().titleMedium.copy(
        fontSize = 20.sp
    ),
    titleLarge = Typography().titleLarge.copy(
        fontWeight = FontWeight.SemiBold
    )
)
