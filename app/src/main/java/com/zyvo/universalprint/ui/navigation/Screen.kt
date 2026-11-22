package com.zyvo.universalprint.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Papers : Screen("papers")
    data object Canvas : Screen("canvas/{documentId}") {
        fun createRoute(documentId: String) = "canvas/$documentId"
    }
    data object Preview : Screen("preview/{documentId}") {
        fun createRoute(documentId: String) = "preview/$documentId"
    }
    data object Printers : Screen("printers")
    data object Settings : Screen("settings")
}
