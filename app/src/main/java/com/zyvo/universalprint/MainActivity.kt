package com.zyvo.universalprint

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zyvo.universalprint.ui.navigation.Screen
import com.zyvo.universalprint.ui.screens.canvas.CanvasScreen
import com.zyvo.universalprint.ui.screens.home.HomeScreen
import com.zyvo.universalprint.ui.screens.papers.PapersScreen
import com.zyvo.universalprint.ui.screens.preview.PreviewScreen
import com.zyvo.universalprint.ui.screens.printers.PrintersScreen
import com.zyvo.universalprint.ui.screens.settings.SettingsScreen
import com.zyvo.universalprint.ui.theme.UniversalPrintTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniversalPrintTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    UniversalPrintNavHost()
                }
            }
        }
    }
}

@Composable
fun UniversalPrintNavHost() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNewDocument = { paperId ->
                    // Create document placeholder handled in HomeScreen
                },
                onOpenCanvas = { id -> nav.navigate(Screen.Canvas.createRoute(id)) },
                onOpenPapers = { nav.navigate(Screen.Papers.route) },
                onOpenPrinters = { nav.navigate(Screen.Printers.route) },
                onOpenSettings = { nav.navigate(Screen.Settings.route) }
            )
        }
        composable(Screen.Papers.route) {
            PapersScreen(
                onBack = { nav.popBackStack() },
                onDocumentCreated = { id -> nav.navigate(Screen.Canvas.createRoute(id)) }
            )
        }
        composable(
            Screen.Canvas.route,
            arguments = listOf(navArgument("documentId") { type = NavType.StringType })
        ) { backStack ->
            val id = backStack.arguments?.getString("documentId") ?: ""
            CanvasScreen(documentId = id, onBack = { nav.popBackStack() }, onPreview = { nav.navigate(Screen.Preview.createRoute(id)) })
        }
        composable(
            Screen.Preview.route,
            arguments = listOf(navArgument("documentId") { type = NavType.StringType })
        ) { backStack ->
            val id = backStack.arguments?.getString("documentId") ?: ""
            PreviewScreen(documentId = id, onBack = { nav.popBackStack() })
        }
        composable(Screen.Printers.route) {
            PrintersScreen(onBack = { nav.popBackStack() })
        }
        composable(Screen.Settings.route) {
            SettingsScreen(onBack = { nav.popBackStack() })
        }
    }
}
