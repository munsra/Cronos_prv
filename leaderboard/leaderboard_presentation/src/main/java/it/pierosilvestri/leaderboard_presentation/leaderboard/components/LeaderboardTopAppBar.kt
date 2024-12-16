package it.pierosilvestri.leaderboard_presentation.leaderboard.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import it.pierosilvestri.leaderboard_presentation.R
import it.pierosilvestri.leaderboard_presentation.leaderboard.LeaderboardAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardTopAppBar(
    onClick: (LeaderboardAction) -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.leaderboard_screen_title)
            )
        },
        actions = {
            IconButton(onClick = {
                onClick(LeaderboardAction.SyncToCloud)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.sync),
                    contentDescription = "Sync",
                )
            }
            IconButton(onClick = {
                onClick(LeaderboardAction.ExportToExcel)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.download),
                    contentDescription = "Sync",
                )
            }
        },
    )
}