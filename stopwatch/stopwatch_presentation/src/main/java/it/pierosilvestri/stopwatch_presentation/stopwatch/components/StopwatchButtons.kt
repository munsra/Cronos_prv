package it.pierosilvestri.stopwatch_presentation.stopwatch.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import it.pierosilvestri.core_ui.theme.Blue
import it.pierosilvestri.core_ui.theme.Green
import it.pierosilvestri.core_ui.theme.Light
import it.pierosilvestri.core_ui.theme.Red
import it.pierosilvestri.stopwatch_domain.utils.StopwatchState
import it.pierosilvestri.stopwatch_presentation.R
import it.pierosilvestri.stopwatch_presentation.stopwatch.StopwatchAction
import it.pierosilvestri.stopwatch_presentation.stopwatch.StopwatchTestConst

@Composable
fun StopwatchButtons(
    modifier: Modifier = Modifier,
    stopwatchState: StopwatchState,
    onAction: (StopwatchAction) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
    ) {
        Button(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(0.5f)
                .testTag(StopwatchTestConst.STOPWATCH_BUTTON),
            onClick = {
                when (stopwatchState) {
                    StopwatchState.Started -> onAction(StopwatchAction.OnLap)
                    StopwatchState.Stopped -> onAction(StopwatchAction.OnResume)
                    StopwatchState.Idle -> onAction(StopwatchAction.OnStart)
                    else -> Unit
                }
            }, colors = ButtonDefaults.buttonColors(
                containerColor = if (stopwatchState == StopwatchState.Started) Blue else Green,
                contentColor = Color.White
            )
        ) {
            when (stopwatchState) {
                StopwatchState.Started -> Text(stringResource(R.string.lap))
                StopwatchState.Stopped -> Text(stringResource(R.string.resume))
                else -> Text(stringResource(R.string.start))
            }
        }
        Spacer(modifier = Modifier.width(30.dp))
        Button(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(0.5f)
                .testTag(StopwatchTestConst.STOPWATCH_BUTTON_STOP),
            onClick = {
                when (stopwatchState) {
                    StopwatchState.Started -> onAction(StopwatchAction.OnStop)
                    StopwatchState.Stopped -> onAction(StopwatchAction.OnReset)
                    else -> Unit
                }
            },
            enabled = stopwatchState != StopwatchState.Idle,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (stopwatchState == StopwatchState.Started) Red else Light,
                disabledContainerColor = Light
            )
        ) {
            when (stopwatchState) {
                StopwatchState.Started ->
                    Text(
                        text = stringResource(R.string.stop),
                        style = TextStyle(color = Color.White),
                    )

                StopwatchState.Stopped ->
                    Text(
                        text = stringResource(R.string.reset),
                        style = TextStyle(color = Color.White),
                    )

                else -> Text(
                    text = stringResource(R.string.cancel),
                    style = TextStyle(color = Color.Gray),
                )
            }
        }
    }
}