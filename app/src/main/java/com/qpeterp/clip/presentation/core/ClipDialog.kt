package com.qpeterp.clip.presentation.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qpeterp.clip.presentation.theme.Colors

@Composable
fun ClipDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    titleColor: Color = Colors.Black,
    message: String,
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            containerColor = Colors.White,
            titleContentColor = Colors.Black,
            shape = RoundedCornerShape(12.dp),
            title = {
                Text(
                    text = title,
                    color = titleColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            },
            text = {
                Text(
                    text = message,
                    color = Colors.DarkGray,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            },
            confirmButton = {
                Text(
                    text = "확인",
                    color = Colors.Black,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .clickable { onConfirm() }
                        .padding(horizontal = 8.dp)
                )
            },
            dismissButton = {
                Text(
                    text = "취소",
                    color = Colors.DarkGray,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .clickable { onDismiss() }
                        .padding(horizontal = 8.dp)
                )
            }
        )
    }
}