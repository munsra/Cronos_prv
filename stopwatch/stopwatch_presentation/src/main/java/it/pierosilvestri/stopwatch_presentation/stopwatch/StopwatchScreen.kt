package it.pierosilvestri.stopwatch_presentation.stopwatch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import it.pierosilvestri.core.domain.model.Lap
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.Session
import it.pierosilvestri.core_ui.theme.Blue
import it.pierosilvestri.core_ui.theme.Green
import it.pierosilvestri.core_ui.theme.Light
import it.pierosilvestri.core_ui.theme.Red
import it.pierosilvestri.stopwatch_domain.utils.StopwatchState
import it.pierosilvestri.stopwatch_presentation.stopwatch.mapper.toLapString
import org.koin.androidx.compose.koinViewModel

@Composable
fun StopwatchScreenRoot(
    viewModel: StopwatchViewModel = koinViewModel(),
    playerId: String,
    sessionId: String
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) {
        viewModel.loadData(playerId, sessionId)
    }

    StopwatchScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@Composable
fun StopwatchScreen(
    state: StopwatchScreenState,
    onAction: (StopwatchAction) -> Unit,
) {
    if (state.isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(32.dp),
            )
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(30.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.weight(weight = 1f)
            ) {
                Text(
                    text = state.player?.fullname ?: "", style = TextStyle(
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
                Text(
                    text = "${state.session?.distance.toString()} Meters", style = TextStyle(
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(weight = 2f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${state.minutes}:", style = TextStyle(
                        fontSize = MaterialTheme.typography.displayLarge.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = if (state.stopwatchState != StopwatchState.Started) Color.Black else Blue
                    )
                )
                Text(
                    text = "${state.seconds}:", style = TextStyle(
                        fontSize = MaterialTheme.typography.displayLarge.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = if (state.stopwatchState != StopwatchState.Started) Color.Black else Blue
                    )
                )
                Text(
                    text = state.centiseconds, style = TextStyle(
                        fontSize = MaterialTheme.typography.displayLarge.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = if (state.stopwatchState != StopwatchState.Started) Color.Black else Blue
                    )
                )
            }
            Row(
                modifier = Modifier.weight(weight = 2f),
                horizontalArrangement = Arrangement.Center,
            ) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(0.5f),
                    onClick = {
                        when (state.stopwatchState) {
                            StopwatchState.Started -> onAction(StopwatchAction.OnLap)
                            StopwatchState.Stopped -> onAction(StopwatchAction.OnResume)
                            StopwatchState.Idle -> onAction(StopwatchAction.OnStart)
                            else -> Unit
                        }
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = if (state.stopwatchState == StopwatchState.Started) Blue else Green,
                        contentColor = Color.White
                    )
                ) {
                    when (state.stopwatchState) {
                        StopwatchState.Started -> Text("Lap")
                        StopwatchState.Stopped -> Text("Resume")
                        else -> Text("Start")
                    }
                }
                Spacer(modifier = Modifier.width(30.dp))
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(0.5f),
                    onClick = {
                        when (state.stopwatchState) {
                            StopwatchState.Started -> onAction(StopwatchAction.OnStop)
                            StopwatchState.Stopped -> onAction(StopwatchAction.OnReset)
                            else -> Unit
                        }
                    },
                    enabled = state.stopwatchState != StopwatchState.Canceled && state.stopwatchState != StopwatchState.Idle,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (state.stopwatchState == StopwatchState.Started) Red else Light,
                        disabledContainerColor = Light
                    )
                ) {
                    when (state.stopwatchState) {
                        StopwatchState.Started ->
                            Text(
                                text = "Stop",
                                style = TextStyle(color = Color.White),
                            )

                        StopwatchState.Stopped ->
                            Text(
                                text = "Reset",
                                style = TextStyle(color = Color.White),
                            )
                        else -> Text(
                            text = "Cancel",
                            style = TextStyle(color = Color.Gray),
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.weight(weight = 4f),
                horizontalArrangement = Arrangement.Center,
            ) {
                if(state.laps.isNotEmpty()){
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        LazyColumn {
                            itemsIndexed(state.laps.sortedByDescending { it.datetime }) { index, lap ->
                                Text(
                                    text ="Lap ${state.laps.size - index} : ${lap.totalTime.toLapString()}",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    style = TextStyle(
                                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                        fontWeight = FontWeight.Bold,
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewStopwatchScreen() {
    val state = StopwatchScreenState(
        isLoading = false,
        minutes = "00",
        seconds = "00",
        centiseconds = "01",
        stopwatchState = StopwatchState.Canceled,
        player = Player(
            id = "a",
            fullname = "Piero Silvestri",
            sessions = null,
            pictures = null,
        ),
        session = Session(
            id = "s",
            distance = 1000,
            laps = null,
            startDate = 0L
        ),
        laps = listOf(
            Lap(60, 50L),
            Lap(1000, 51L),
        ),
    )

    StopwatchScreen(
        state = state,
        onAction = {}
    )
}