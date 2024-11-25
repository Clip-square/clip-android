package com.qpeterp.clip.presentation.feature.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.CollectionsBookmark
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.qpeterp.clip.presentation.feature.main.history.screen.HistoryScreen
import com.qpeterp.clip.presentation.feature.main.setting.screen.SettingScreen
import com.qpeterp.clip.presentation.feature.main.setup.screen.SetupScreen
import com.qpeterp.clip.presentation.feature.main.viewmodel.MainViewModel
import com.qpeterp.clip.presentation.theme.Colors

@ExperimentalMaterial3Api
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel(),
) {
    var selectedItem by remember { mutableStateOf(viewModel.selectedTab) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MyTopAppBar(selectedItem)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .zIndex(1f)
                .background(Colors.White)
        ) {
            when (selectedItem) {
                0 -> HistoryScreen(navController)
                1 -> SetupScreen(navController)
                2 -> SettingScreen(navController)
            }
        }
        MyBottomNavigation(
            selectedItem
        ) {
            viewModel.updateSelectedTab(it)
            selectedItem = it
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(selectedItem: Int) {
    TopAppBar(
        title = {
            Box( // 시작 정렬을 위해 Box 사용
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.CenterStart // 시작 정렬
            ) {
                Text(
                    text = when (selectedItem) {
                        0 -> "전체 회의록"
                        1 -> "회의 생성"
                        2 -> "조직 설정"
                        else -> ""
                    },
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Colors.White
        ),
    )
}

@Composable
fun MyBottomNavigation(
    basicSelectedItem: Int,
    selectItem: (Int) -> Unit,
) {
    val items = listOf(0, 1, 2)
    // 선택된 아이템을 저장할 상태 변수
    var selectedItem by remember { mutableIntStateOf(basicSelectedItem) }

    NavigationBar(
        containerColor = Colors.LightGray,
        contentColor = Colors.LightGray,
        modifier = Modifier.zIndex(2f)
    ) {
        items.fastForEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = when (item) {
                            0 -> Icons.Outlined.CollectionsBookmark
                            1 -> Icons.Outlined.ChatBubbleOutline
                            2 -> Icons.Filled.Settings
                            else -> return@NavigationBarItem
                        },
                        contentDescription = null
                    )
                },
                selected = selectedItem == item, // 현재 선택된 아이템 확인
                onClick = {
                    if (selectedItem == item) return@NavigationBarItem

                    selectedItem = item // 클릭 시 선택된 아이템 업데이트
                    selectItem(selectedItem)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Colors.Black, // 선택된 아이콘 색상
                    unselectedIconColor = Color.DarkGray, // 비선택 아이콘 색상
                    selectedTextColor = Color.White, // 선택된 텍스트 색상
                    unselectedTextColor = Color.Gray, // 비선택 텍스트 색상
                    indicatorColor = Colors.DarkGray
                )
            )
        }
    }
}