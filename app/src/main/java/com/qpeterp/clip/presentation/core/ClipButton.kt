package com.qpeterp.clip.presentation.core

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qpeterp.clip.presentation.theme.Colors

@Composable
fun ClipButton(
    text: String,
    textColor: Color = Colors.White, // 기본 텍스트 색
    backgroundColor: Color = Colors.Black,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Text(
        text = text,
        color = textColor,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(6.dp) // 모서리 둥글게
            )
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp, horizontal = 32.dp), // 내부 여백
        textAlign = TextAlign.Center
    )
}