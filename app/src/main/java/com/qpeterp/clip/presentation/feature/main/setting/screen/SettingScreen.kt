package com.qpeterp.clip.presentation.feature.main.setting.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.qpeterp.clip.common.Constant
import com.qpeterp.clip.presentation.core.ClipDialog
import com.qpeterp.clip.presentation.feature.main.setting.viewmodel.SettingViewModel
import com.qpeterp.clip.presentation.theme.Colors
import kotlinx.coroutines.CoroutineScope

@Composable
fun SettingScreen(
    navController: NavController,
    viewModel: SettingViewModel = hiltViewModel()
) {
    var resignDialogState by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .padding(20.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "계정 관리",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Colors.DarkGray
            )
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Colors.White, RoundedCornerShape(12.dp))
                    .padding(20.dp)
            ) {
                SettingCard(
                    icon = Icons.Outlined.Logout,
                    iconColor = Colors.Red,
                    iconBackgroundColor = Colors.LightRed,
                    label = "로그아웃",
                    onClick = {
                        viewModel.logout(
                            onSuccess = {
                                navController.navigate("login") {
                                    popUpTo(0)
                                }
                            },
                            onFailed = {
                                Log.d(Constant.TAG, "로그아웃 실패")
                            }
                        )
                    }
                ) {}
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(Colors.LightGray)
                )
                SettingCard(
                    icon = Icons.Outlined.Delete,
                    iconColor = Colors.Red,
                    iconBackgroundColor = Colors.LightRed,
                    label = "회원 탈퇴",
                    contentColor = Colors.Red,
                    onClick = {
                        resignDialogState = true
                    }
                ) {}
            }
        }

        ClipDialog(
            showDialog = resignDialogState,
            title = "회원 탈퇴",
            titleColor = Colors.Red,
            message = "정말 회원을 탈퇴하시겠습니까?\n모든 정보가 사라집니다.",
            onConfirm = {
                resignDialogState = false
                navController.navigate("login") {
                    popUpTo(0)
                }
            },
            onDismiss = {
                resignDialogState = false
            }
        )
    }
}

@Composable
private fun SettingCard(
    icon: ImageVector,
    iconColor: Color = Colors.Black,
    iconBackgroundColor: Color = Colors.White,
    label: String,
    contentColor: Color = Colors.Black,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                tint = iconColor,
                contentDescription = null,
                modifier = Modifier
                    .background(iconBackgroundColor, RoundedCornerShape(8.dp))
                    .padding(12.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = label,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                color = contentColor
            )
        }

        content()
    }
}