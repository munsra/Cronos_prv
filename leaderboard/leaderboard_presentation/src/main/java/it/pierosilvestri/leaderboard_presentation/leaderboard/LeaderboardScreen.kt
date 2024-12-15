package it.pierosilvestri.leaderboard_presentation.leaderboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import it.pierosilvestri.core.domain.model.Lap
import it.pierosilvestri.leaderboard_presentation.R
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.Session
import it.pierosilvestri.leaderboard_presentation.leaderboard.components.NewSessionDialog

import org.koin.androidx.compose.koinViewModel

@Composable
fun LeaderboardScreenRoot(
    viewModel: LeaderboardViewModel = koinViewModel(),
    onNextClick: (player: Player, session: Session) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true,){
        viewModel.uiEvent.collect {
            when(it) {
                is LeaderboardEvent.OpenNextPage -> {
                    onNextClick(it.player, it.session)
                }
            }
        }
    }

    LeaderboardScreen(
        state = state,
        onAction = viewModel::onAction
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(
    state: LeaderboardState,
    onAction: (LeaderboardAction) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAction(LeaderboardAction.OpenNewSession)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_new_session)
                )
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.leaderboard_screen_title)
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            LazyColumn {
               items(state.leaders){
                   Text(text = it.fullname)
               }
            }
        }

        if (state.isNewSessionDialogOpen) {
            NewSessionDialog(
                players = state.players,
                onDismissRequest = { onAction(LeaderboardAction.DismissNewSessionDialog) },
                onConfirmation = { player, session ->
                    onAction(LeaderboardAction.ConfirmNewSession(player, session))
                }
            )
        }
    }
}

@Preview
@Composable
fun LeaderboardScreenPreview() {
    val state = LeaderboardState(
        leaders = listOf(
            Player(
                id = "1",
                fullname = "Piero Silvestri",
                sessions = listOf(
                    Session(
                        id = "1",
                        distance = 1000,
                        startDate = 10L,
                        laps = listOf(
                            Lap(
                                totalTime = 1000,
                                datetime = 1
                            )
                        )
                    )
                ),
                pictures = null,
            ),
            Player(
                id = "2",
                fullname = "Piero Silvestri",
                sessions = listOf(
                    Session(
                        id = "1",
                        distance = 1000,
                        startDate = 10L,
                        laps = listOf(
                            Lap(
                                totalTime = 1000,
                                datetime = 1
                            )
                        )
                    )
                ),
                pictures = null
            )
        )
    )
    LeaderboardScreen(
        state = state,
        onAction = {}
    )
}