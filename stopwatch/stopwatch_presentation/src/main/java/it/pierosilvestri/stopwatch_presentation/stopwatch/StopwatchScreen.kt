package it.pierosilvestri.stopwatch_presentation.stopwatch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import it.pierosilvestri.core_ui.theme.Blue
import it.pierosilvestri.core_ui.theme.Light
import it.pierosilvestri.core_ui.theme.Red
import it.pierosilvestri.stopwatch_domain.utils.StopwatchState
import org.koin.androidx.compose.koinViewModel

@Composable
fun StopwatchScreenRoot(viewModel: StopwatchViewModel = koinViewModel()) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    StopwatchScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@Composable
fun StopwatchScreen(
    state: StopwatchScreenState,
    onAction: (StopwatchAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.weight(weight = 9f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${state.minutes}:", style = TextStyle(
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = if (state.stopwatchState != StopwatchState.Started) Color.Black else Blue
                )
            )
            Text(
                text = "${state.seconds}:", style = TextStyle(
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = if (state.stopwatchState != StopwatchState.Started) Color.Black else Blue
                )
            )
            Text(
                text = state.centiseconds, style = TextStyle(
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = if (state.stopwatchState != StopwatchState.Started) Color.Black else Blue
                )
            )
        }
        Row(modifier = Modifier.weight(weight = 1f)) {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(0.8f),
                onClick = {
                    when (state.stopwatchState) {
                        StopwatchState.Started -> onAction(StopwatchAction.OnStop)
                        StopwatchState.Stopped -> onAction(StopwatchAction.OnStart)
                        StopwatchState.Idle -> onAction(StopwatchAction.OnStart)
                        else -> Unit
                    }
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = if (state.stopwatchState == StopwatchState.Started) Red else Blue,
                    contentColor = Color.White
                )
            ) {
                when (state.stopwatchState) {
                    StopwatchState.Started -> Text("Stop")
                    StopwatchState.Stopped -> Text("Resume")
                    else -> Text("Start")
                }
            }
            Spacer(modifier = Modifier.width(30.dp))
            Button(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(0.8f),
                onClick = {
                    onAction(StopwatchAction.OnReset)
                },
                enabled = state.centiseconds != "00" && state.stopwatchState == StopwatchState.Stopped,
                colors = ButtonDefaults.buttonColors(disabledContainerColor = Light)
            ) {
                Text(
                    text = "Cancel",
                    style = if(state.centiseconds != "00" && state.stopwatchState == StopwatchState.Stopped) TextStyle(color = Color.White) else TextStyle(color = Color.Gray),
                )
            }
        }
    }
}