package com.qpeterp.clip.presentation.root.navigation

sealed class NavGroup {
    data object Auth: NavGroup() {
        const val LOGIN = "login"
        const val REGISTER_ID = "registerId"
        const val REGISTER_NAME = "registerName"
        const val REGISTER_PASSWORD = "registerPassword"
        const val REGISTER_COMPLETE = "registerComplete"
    }

    data object Main: NavGroup() {
        const val MAIN = "main"
        const val MEETING_HISTORY = "meetingHistory"
        const val SETUP = "setup"
        const val SETTING = "setting"
    }

    data object Features: NavGroup() {
        const val MANAGE_TEAM = "manageTeam"
        const val MEETING = "meeting"
        const val END = "end"
        const val HISTORY_MEETING = "historyMeeting"
    }
}