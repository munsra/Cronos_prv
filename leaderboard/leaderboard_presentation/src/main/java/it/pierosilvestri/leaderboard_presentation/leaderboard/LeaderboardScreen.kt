package it.pierosilvestri.leaderboard_presentation.leaderboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import it.pierosilvestri.core.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun LeaderboardScreenRoot(viewModel: LeaderboardViewModel = koinViewModel()) {

    val state by viewModel.state.collectAsStateWithLifecycle()

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

        }
    }
}