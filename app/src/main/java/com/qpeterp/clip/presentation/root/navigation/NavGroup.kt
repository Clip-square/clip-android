package com.qpeterp.clip.presentation.root.navigation

sealed class NavGroup {
    data object Main: NavGroup() {
        const val SETUP = "setup"
        const val MEETING = "meeting"
        const val END = "end"
    }
}