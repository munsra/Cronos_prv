package it.pierosilvestri.calorytracker.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
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
            LeaderboardScreenRoot(
                onNextClick = { player, session ->
                    navController.navigate(
                        Route.STOPWATCH + "/${player.id}/${session.id}"
                    )
                }
            )
        }
        composable(
            route = Route.STOPWATCH + "/{playerId}/{sessionId}",
            arguments = listOf(
                navArgument("playerId") {
                    type = NavType.StringType
                },
                navArgument("sessionId") {
                    type = NavType.StringType
                },
            )
        ) {
            val playerId = it.arguments?.getString("playerId")!!
            val sessionId = it.arguments?.getString("sessionId")!!
            StopwatchScreenRoot(
                playerId = playerId,
                sessionId = sessionId
            )
        }
    }
}