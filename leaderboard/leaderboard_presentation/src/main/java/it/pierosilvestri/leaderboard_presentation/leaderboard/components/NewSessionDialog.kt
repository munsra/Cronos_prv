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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.Session
import java.util.Calendar
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
fun NewSessionDialog(
    players: List<Player>,
    onDismissRequest: () -> Unit,
    onConfirmation: (player: Player, session: Session) -> Unit,
) {
    var selectedPlayer by remember { mutableStateOf<Player?>(null) }
    var sessionDistance by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = { Text(text = "Dialog Title") },
        text = {
            if (selectedPlayer == null) {
                LazyColumn {
                    items(players) { player ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedPlayer = player }
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = player.fullname,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            if (selectedPlayer == player) {
                                Text(
                                    text = "✔",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
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
                        Text(
                            text = selectedPlayer!!.fullname,
                            style = MaterialTheme.typography.bodyMedium
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
                        label = { Text("Distance") }
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
                            distance = sessionDistance.toInt(),
                            id = Uuid.random().toString(),
                            startDate = Calendar.getInstance().time.time,
                            laps = null
                        )
                    )
                }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text("Cancel")
            }
        }
    )
}