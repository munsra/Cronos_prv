package it.pierosilvestri.cronos

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.common.truth.Truth.assertThat
import it.pierosilvestri.calorytracker.navigation.Route
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.repository.PlayerRepository
import it.pierosilvestri.core_ui.theme.CronosTheme
import it.pierosilvestri.leaderboard_presentation.leaderboard.LeaderboardScreenRoot
import it.pierosilvestri.leaderboard_presentation.leaderboard.LeaderboardTestConst
import it.pierosilvestri.leaderboard_presentation.leaderboard.LeaderboardViewModel
import it.pierosilvestri.stopwatch_presentation.stopwatch.StopwatchScreenRoot
import it.pierosilvestri.stopwatch_presentation.stopwatch.StopwatchTestConst
import it.pierosilvestri.stopwatch_presentation.stopwatch.StopwatchViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject


@ExperimentalComposeUiApi
class CronosOverviewE2E: KoinTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val playerRepository: PlayerRepository by inject()

    private lateinit var navController: NavHostController

    private val leaderboardViewModel: LeaderboardViewModel by inject()
    private val stopwatchViewModel: StopwatchViewModel by inject()

    @Before
    fun setUp() {
        initDatabase()
        composeRule.setContent {
            navController = rememberNavController()
            CronosTheme {
                NavHost(
                    navController = navController,
                    startDestination = Route.LEADERBOARD
                ) {
                    composable(Route.LEADERBOARD) {
                        LeaderboardScreenRoot(
                            viewModel = leaderboardViewModel,
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
                        val playerId = it.arguments?.getString("playerId")
                        val sessionId = it.arguments?.getString("sessionId")
                        StopwatchScreenRoot(
                            viewModel = stopwatchViewModel,
                            onNavigateBack = {
                                navController.popBackStack()
                            },
                            playerId = playerId!!,
                            sessionId = sessionId!!
                        )
                    }
                }
            }
        }
    }

    private fun initDatabase() = runBlocking {
        // Step 1: Add 2 players to the repository
        val players = listOf(
            Player(
                id = "1",
                fullname = "Player 1",
                sessions = emptyList(),
                pictures = null
            ),
            Player(
                id = "2",
                fullname = "Player 2",
                sessions = emptyList(),
                pictures = null
            ),
        )
        playerRepository.addPlayers(players)
    }


    @Test
    fun openNewSessionPopup(): Unit = runBlocking {

        val players = playerRepository.getPlayers().first()
        assertThat(players.size).isNotEqualTo(0)

        assertThat(
            navController
                .currentDestination
                ?.route
                ?.equals(Route.LEADERBOARD)
        ).isTrue()

        composeRule.onNodeWithTag(String.format(LeaderboardTestConst.LEADERBOARD_ITEM, players[0].id))

        // Scroll to index 2 to ensure it's rendered
        composeRule.onNodeWithTag(String.format(LeaderboardTestConst.LEADERBOARD_ITEM, 0)).assertExists()

        // Assert the text of the item at index 2
        composeRule.onNodeWithTag(String.format(LeaderboardTestConst.LEADERBOARD_ITEM, 0)).assertTextEquals(players.first().fullname)

        composeRule
            .onNodeWithTag(LeaderboardTestConst.NEW_SESSION_DIALOG)
            .assertIsNotDisplayed()

        composeRule
            .onNodeWithTag(LeaderboardTestConst.BUTTON_NEW_SESSION)
            .assertIsDisplayed()
            .performClick()
        composeRule
            .onNodeWithTag(LeaderboardTestConst.NEW_SESSION_DIALOG)
            .assertIsDisplayed()
        composeRule.onNodeWithTag(LeaderboardTestConst.PLAYER_LIST).onChildren().assertCountEquals(players.size)
        val selectedPlayer = players[1]
        composeRule.onNodeWithTag(String.format(LeaderboardTestConst.PLAYER_SELECTED, selectedPlayer.id)).performClick()
        composeRule.onNodeWithTag(LeaderboardTestConst.NEW_SESSION_DIALOG_DISTANCE).performTextInput("1000")
        composeRule.onNodeWithTag(LeaderboardTestConst.NEW_SESSION_DIALOG_OK_BUTTON).performClick()
        composeRule
            .onNodeWithTag(LeaderboardTestConst.NEW_SESSION_DIALOG)
            .assertIsNotDisplayed()
        assertThat(
            navController
                .currentDestination
                ?.route
                ?.startsWith(Route.STOPWATCH)
        ).isTrue()

        composeRule.onNodeWithText("00:00:00").isDisplayed()

        composeRule.onNodeWithTag(StopwatchTestConst.STOPWATCH_BUTTON).assertIsDisplayed().performClick()

    }
}