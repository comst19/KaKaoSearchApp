package com.comst.designsystem.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.comst.designsystem.R

@Composable
@Preview(showBackground = true)
fun TypographyPreview() {
    Column(modifier = Modifier.fillMaxSize()){

        Text(text = "베이스 타이포 titleLarge", style = BaseTypography.titleLarge)

        Text(text = "베이스 타이포 titleMedium", style = BaseTypography.titleMedium)

        Text(text ="베이스 타이포 titleSmall", style = BaseTypography.titleSmall)

        Text(text ="베이스 타이포 bodyLarge", style = BaseTypography.bodyLarge)

        Text(text ="베이스 타이포 bodyMedium", style = BaseTypography.bodyMedium)

        Text(text ="베이스 타이포 bodySmall", style = BaseTypography.bodySmall)

        Spacer(modifier = Modifier.size(16.dp))

        Text(text = "앱바 타이포 titleLarge", style = AppBarTypography.titleLarge)

        Text(text = "앱바 타이포 titleSmall", style = AppBarTypography.titleSmall)

        Text(text = "앱바 타이포 bodyMedium", style = AppBarTypography.bodyMedium)

        Text(text = "앱바 타이포 bodySmall", style = AppBarTypography.bodySmall)

    }
}

val PretendardFont = FontFamily(
    Font(R.font.pretendard_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.pretendard_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.pretendard_extra_bold, FontWeight.ExtraBold, FontStyle.Normal),
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val BaseTypography = Typography(
    titleLarge = TextStyle(
        fontFamily = PretendardFont,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.8.sp
    ),

    titleMedium = TextStyle(
        fontFamily = PretendardFont,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.8.sp
    ),

    titleSmall = TextStyle(
        fontFamily = PretendardFont,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.8.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = PretendardFont,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.8.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = PretendardFont,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.8.sp
    ),


    bodySmall = TextStyle(
        fontFamily = PretendardFont,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.8.sp
    ),


    )

val AppBarTypography = Typography(
    titleLarge = TextStyle(
        fontFamily = PretendardFont,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.8.sp
    ),
    titleSmall = TextStyle(
        fontFamily = PretendardFont,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.8.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = PretendardFont,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.8.sp
    ),

    bodySmall = TextStyle(
        fontFamily = PretendardFont,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.8.sp
    )

)