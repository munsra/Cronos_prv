package it.pierosilvestri.leaderboard_presentation.leaderboard

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import it.pierosilvestri.core.domain.model.Lap
import it.pierosilvestri.leaderboard_presentation.R
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.PlayerPictures
import it.pierosilvestri.core.domain.model.Session
import it.pierosilvestri.core.util.UiText
import it.pierosilvestri.core_ui.components.ErrorDialog
import it.pierosilvestri.leaderboard_presentation.leaderboard.components.LeaderboardTopAppBar
import it.pierosilvestri.leaderboard_presentation.leaderboard.components.LoadingDialog
import it.pierosilvestri.leaderboard_presentation.leaderboard.components.NewSessionDialog
import it.pierosilvestri.leaderboard_presentation.leaderboard.components.PlayerListItem

import org.koin.androidx.compose.koinViewModel

@Composable
fun LeaderboardScreenRoot(
    viewModel: LeaderboardViewModel = koinViewModel(),
    onNextClick: (player: Player, session: Session) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) {
        viewModel.setLeaderboard()
        viewModel.uiEvent.collect {
            when (it) {
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

@Composable
fun LeaderboardScreen(
    state: LeaderboardState,
    onAction: (LeaderboardAction) -> Unit,
) {
    Column {
        Text(
            text = "Leaderboard",
            style = MaterialTheme.typography.headlineLarge
        )
    }
    Scaffold(
        floatingActionButton = {
            if (state.players.isNotEmpty()) {
                FloatingActionButton(
                    modifier = Modifier
                        .testTag(LeaderboardTestConst.BUTTON_NEW_SESSION),
                    onClick = {
                        onAction(LeaderboardAction.OpenNewSession)
                    },
                ) {
                    Text(stringResource(R.string.new_session), modifier = Modifier.padding(16.dp))
                }
            }
        },
        topBar = {
            LeaderboardTopAppBar(
                onClick = onAction
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (state.players.isEmpty()) {
                    Text(
                        text = stringResource(R.string.no_players),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Button(
                    onClick = {
                        onAction(LeaderboardAction.LoadPlayersFromRemote)
                    }
                ) {
                    Text(stringResource(R.string.download_from_remote))
                }
            }
            when {

                state.leaders.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column {
                            Text(
                                text = stringResource(R.string.no_leader),
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.testTag(LeaderboardTestConst.LEADERBOARD_LIST)
                    ) {
                        item {
                            HorizontalDivider()
                        }
                        itemsIndexed(state.leaders) { position, it ->
                            Row(
                                modifier = Modifier.padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${position +1}",
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.width(50.dp),
                                    textAlign = TextAlign.Center
                                )
                                PlayerListItem(
                                    player = it,
                                    modifier = Modifier.testTag(
                                        String.format(
                                            LeaderboardTestConst.LEADERBOARD_ITEM,
                                            position
                                        )
                                    )
                                )
                            }

                            HorizontalDivider()
                        }
                    }
                }
            }
        }
        if (state.isLoading){
            LoadingDialog(
                message = state.loadingMessage
            )
        }
        if (state.isNewSessionDialogOpen) {
            NewSessionDialog(
                players = state.players,
                onDismissRequest = { onAction(LeaderboardAction.DismissNewSessionDialog) },
                onConfirmation = { player, session ->
                    onAction(LeaderboardAction.ConfirmNewSession(player, session))
                },
                modifier = Modifier.testTag(LeaderboardTestConst.NEW_SESSION_DIALOG)
            )
        }
        if (state.errorMessage != null){
            ErrorDialog(
                errorMessage = state.errorMessage,
                onDismissRequest = {
                    onAction(LeaderboardAction.DismissErrorDialog)
                }
            )
        }
    }
}

@Preview
@Composable
fun LeaderboardScreenPreview() {
    val state = LeaderboardState(
        isLoading = false,
        leaders = listOf(
            Player(
                id = "1",
                fullname = "John Doe",
                sessions = null,
                pictures = PlayerPictures(
                    largePicture = "https://randomuser.me/api/portraits/men/91.jpg",
                    mediumPicture = "https://randomuser.me/api/portraits/men/91.jpg",
                )
            ),
            Player(
                id = "1",
                fullname = "John Doe",
                sessions = null,
                pictures = PlayerPictures(
                    largePicture = "https://randomuser.me/api/portraits/men/91.jpg",
                    mediumPicture = "https://randomuser.me/api/portraits/men/91.jpg",
                )
            )
        )
    )
    LeaderboardScreen(
        state = state,
        onAction = {}
    )
}