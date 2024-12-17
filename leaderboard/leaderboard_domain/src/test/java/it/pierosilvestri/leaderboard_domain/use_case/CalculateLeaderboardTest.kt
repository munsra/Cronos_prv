package it.pierosilvestri.leaderboard_domain.use_case

import com.google.common.truth.Truth.assertThat
import it.pierosilvestri.core.domain.model.Lap
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.Session
import org.junit.Before
import org.junit.Test

class CalculateLeaderboardTest {
    private lateinit var calculateLeaderboard: CalculateLeaderboard

    @Before
    fun setUp() {
        calculateLeaderboard = CalculateLeaderboard()
    }

    @Test
    fun `Test Leaderboard Calculation`() {
        val players = listOf(
            Player(
                id = "A",
                fullname = "Player A",
                pictures = null,
                sessions = listOf(
                    Session(
                        id = "",
                        distance = 1000,
                        laps = listOf(
                            Lap(totalTime = 1000, datetime = 2L),
                        ),
                        startDate = 3L
                    )
                )
            ),
            Player(
                id = "B",
                fullname = "Player B",
                pictures = null,
                sessions = listOf(
                    Session(
                        id = "",
                        distance = 10000,
                        laps = listOf(
                            Lap(totalTime = 1000, datetime = 0L),
                        ),
                        startDate = 1L
                    )
                )
            ),
        )

        val leaderboard = calculateLeaderboard(players);

        val expectedLeaders = listOf(
            Player(
                id = "B",
                fullname = "Player B",
                pictures = null,
                sessions = listOf(
                    Session(
                        id = "",
                        distance = 10000,
                        laps = listOf(
                            Lap(totalTime = 1000, datetime = 0L),
                        ),
                        startDate = 1L
                    )
                )
            ),
            Player(
                id = "A",
                fullname = "Player A",
                pictures = null,
                sessions = listOf(
                    Session(
                        id = "",
                        distance = 1000,
                        laps = listOf(
                            Lap(totalTime = 1000, datetime = 2L),
                        ),
                        startDate = 3L
                    )
                )
            )
        )


        assertThat(leaderboard).isEqualTo(expectedLeaders)
    }
}