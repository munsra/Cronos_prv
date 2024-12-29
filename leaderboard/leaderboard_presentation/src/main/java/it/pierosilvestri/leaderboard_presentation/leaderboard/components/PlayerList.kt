package it.pierosilvestri.leaderboard_presentation.leaderboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.PlayerPictures
import it.pierosilvestri.leaderboard_presentation.leaderboard.LeaderboardTestConst

@Composable
fun PlayerList(
    players: List<Player>,
    modifier: Modifier = Modifier,
    onPlayerClick: (Player) -> Unit = {},
    scrollState: LazyListState = rememberLazyListState(),
) {
    LazyColumn(
        modifier = modifier.testTag(LeaderboardTestConst.PLAYER_LIST),
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(
            items = players,
            key = { it.id }
        ) { player ->
            PlayerListItem(
                player = player,
                modifier = Modifier
                    .widthIn(max = 300.dp)
                    .fillMaxWidth()
                    .testTag(String.format(LeaderboardTestConst.PLAYER_SELECTED, player.id)),
                onClick = {
                    onPlayerClick(it)
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewPlayerList() {
    val player = Player(
        id = "1",
        fullname = "John Doe",
        pictures = PlayerPictures(
            largePicture = "https://randomuser.me/api/portraits/men/91.jpg",
            mediumPicture = "https://randomuser.me/api/portraits/men/91.jpg",
            smallPicture = "https://randomuser.me/api/portraits/men/91.jpg",
        ),
        sessions = emptyList()
    )
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            PlayerList(
                players = listOf(player),
                onPlayerClick = {}
            )
        }
    }
}