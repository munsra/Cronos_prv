package it.pierosilvestri.leaderboard_presentation.leaderboard.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.PlayerPictures
import it.pierosilvestri.core.domain.model.Session
import it.pierosilvestri.core_ui.components.CustomConfirmDialog
import it.pierosilvestri.leaderboard_presentation.R
import java.util.Calendar
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
fun NewSessionDialog(
    players: List<Player>,
    onDismissRequest: () -> Unit,
    onConfirmation: (player: Player, session: Session) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedPlayer by remember { mutableStateOf<Player?>(null) }
    var sessionDistance by remember { mutableStateOf("") }

    CustomConfirmDialog(
        modifier = modifier,
        dialogTitle = stringResource(R.string.new_session),
        onDismissRequest = { onDismissRequest() },
        content = {
            if (selectedPlayer == null) {
                PlayerList(
                    players = players,
                    onPlayerClick = { player ->
                        selectedPlayer = player
                    }
                )
            }
            if (selectedPlayer != null) {

                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedPlayer = null }
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        PlayerListItem(
                            player = selectedPlayer!!
                        )
                        Text(
                            text = "✔",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    TextField(
                        value = sessionDistance,
                        onValueChange = {
                            val pattern = Regex("^\\d+\$")
                            if (it.isEmpty() || it.matches(pattern)) {
                                sessionDistance = it
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text(stringResource(R.string.distance)) }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                enabled = selectedPlayer != null && sessionDistance.isNotBlank() && sessionDistance.toInt() > 0,
                onClick = {
                    onConfirmation(
                        selectedPlayer!!, Session(
                            id = Uuid.random().toString(),
                            distance = sessionDistance.toInt(),
                            startDate = Calendar.getInstance().time.time,
                            laps = emptyList()
                        )
                    )
                }) {
                Text(stringResource(R.string.dialog_positive_button))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(stringResource(R.string.dialog_negative_button))
            }
        }
    )
}

@Preview
@Composable
fun PreviewNewSessionDialog() {

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
            NewSessionDialog(
                players = listOf(player),
                onDismissRequest = {},
                onConfirmation = { _, _ -> }
            )
        }
    }
}
