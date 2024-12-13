package it.pierosilvestri.calorytracker.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import it.pierosilvestri.leaderboard_presentation.leaderboard.LeaderboardScreenRoot
import it.pierosilvestri.stopwatch_presentation.stopwatch.StopwatchScreenRoot

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Navigator(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.LEADERBOARD
    ) {
        composable(Route.LEADERBOARD) {
            LeaderboardScreenRoot()
        }
        composable(Route.STOPWATCH) {
            StopwatchScreenRoot()
        }
    }
}