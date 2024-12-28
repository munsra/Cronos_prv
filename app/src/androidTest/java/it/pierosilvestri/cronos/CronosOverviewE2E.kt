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
import it.pierosilvestri.core.data.database.mock.MockDatabase
import it.pierosilvestri.core.data.remote.RemotePlayerDataSource
import it.pierosilvestri.core.data.remote.mock.MockKtorRemotePlayerDataSource
import it.pierosilvestri.core.data.repository.mock.MockLapRepositoryImpl
import it.pierosilvestri.core.data.repository.mock.MockPlayerRepositoryImpl
import it.pierosilvestri.core.data.repository.mock.MockSessionRepositoryImpl
import it.pierosilvestri.core.domain.repository.LapRepository
import it.pierosilvestri.core.domain.repository.PlayerRepository
import it.pierosilvestri.core.domain.repository.SessionRepository
import it.pierosilvestri.core_ui.theme.CronosTheme
import it.pierosilvestri.leaderboard_presentation.leaderboard.LeaderboardScreenRoot
import it.pierosilvestri.stopwatch_presentation.stopwatch.StopwatchScreenRoot
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.GlobalContext.stopKoin

@ExperimentalComposeUiApi
class CronosOverviewE2E {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var mockDatabase: MockDatabase
    private lateinit var remotePlayerDataSource: RemotePlayerDataSource
    private lateinit var playerRepository: PlayerRepository
    private lateinit var sessionRepository: SessionRepository
    private lateinit var lapRepository: LapRepository

    private lateinit var navController: NavHostController


    @Before
    fun setUp() {

        mockDatabase = MockDatabase()
        remotePlayerDataSource = MockKtorRemotePlayerDataSource()
        playerRepository = MockPlayerRepositoryImpl(mockDatabase, remotePlayerDataSource)
        sessionRepository = MockSessionRepositoryImpl(mockDatabase)
        lapRepository = MockLapRepositoryImpl(mockDatabase)



        composeRule.setContent {
            CronosTheme {
                navController = rememberNavController()
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
                        StopwatchScreenRoot(
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }

    @After
    fun tearDown() {
        stopKoin() // Stop Koin after each test
    }

    @Test
    fun openNewSessionPopup() {
        assertThat(
            navController
                .currentDestination
                ?.route
                ?.startsWith(Route.LEADERBOARD)
        ).isTrue()

        composeRule
            .onNodeWithText("New Session")
            .assertIsDisplayed()
        composeRule
            .onNodeWithText("New Session")
            .performClick()

        Thread.sleep(3000)
    }

}