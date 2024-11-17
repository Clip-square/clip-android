package com.qpeterp.clip.presentation.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.qpeterp.clip.presentation.theme.Colors

@Composable
fun ClipTextField(
    label: String,
    idValue: Boolean = false,
    currentText: String = "",
    onDelete: (Int) -> Unit = {},
    idValueChange: (Int, String) -> Unit = { _, _ -> },
    onValueChange: (String) -> Unit = {}, // 상태 변경을 처리할 콜백 추가
) {
    val text = remember { mutableStateOf(currentText) }
    val scrollState = rememberScrollState()
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Colors.LightGray, RoundedCornerShape(12.dp))
            .heightIn(max = 72.dp)
    ) {
        TextField(
            value = text.value,
            onValueChange = { it ->
                text.value = it
                onValueChange(it)
                if (idValue) {
                    idValueChange(label.toInt(), it)
                }
            },
            label = { Text(text = label, color = Colors.Black) },
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState), // 텍스트 입력 부분만 스크롤 가능
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Colors.Transparent,
                unfocusedContainerColor = Colors.Transparent,
                focusedIndicatorColor = Colors.Transparent,
                unfocusedIndicatorColor = Colors.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            shape = RoundedCornerShape(12.dp)
        )

        if (idValue) {
            IconButton(
                onClick = { onDelete(label.toInt()) },
                modifier = Modifier
                    .padding(end = 8.dp) // 오른쪽 여백 설정
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "icon to remove sub topic"
                )
            }
        }
    }

}