package it.pierosilvestri.stopwatch_presentation.stopwatch

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import it.pierosilvestri.core.domain.model.Lap
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.Session
import it.pierosilvestri.core_ui.components.CustomConfirmDialog
import it.pierosilvestri.stopwatch_domain.utils.StopwatchState
import it.pierosilvestri.stopwatch_presentation.stopwatch.mapper.toLapString
import org.koin.androidx.compose.koinViewModel
import it.pierosilvestri.stopwatch_presentation.R
import it.pierosilvestri.stopwatch_presentation.stopwatch.components.LapsDetailChart
import it.pierosilvestri.stopwatch_presentation.stopwatch.components.LapsList
import it.pierosilvestri.stopwatch_presentation.stopwatch.components.StopwatchButtons
import it.pierosilvestri.stopwatch_presentation.stopwatch.components.StopwatchDisplay
import it.pierosilvestri.stopwatch_presentation.stopwatch.components.StopwatchSegmentedButtons

@Composable
fun StopwatchScreenRoot(
    viewModel: StopwatchViewModel = koinViewModel(),
    onNavigateBack: () -> Unit,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) {
        viewModel.loadData()
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is StopwatchEvent.NavigateBack -> {
                    onNavigateBack()
                }
            }
        }
    }

    BackHandler(true) {
        viewModel.onAction(StopwatchAction.OnBackButtonPressed)
    }

    StopwatchScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StopwatchScreen(
    state: StopwatchScreenState,
    onAction: (StopwatchAction) -> Unit,
) {
    val ctx = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.player?.fullname ?: "") },
                actions = {
                    Button(
                        onClick = {
                            onAction(StopwatchAction.OnSaveButtonPressed)
                        },
                        enabled = state.session?.laps != null &&
                                state.session?.laps!!.isNotEmpty() &&
                                state.stopwatchState == StopwatchState.Stopped,
                    ) {
                        Text(stringResource(R.string.save))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onAction(StopwatchAction.OnBackButtonPressed)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
            )
        },
    ) {
        if (state.isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
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
                    .padding(it)
                    .padding(30.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier.weight(weight = 1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${state.session?.distance.toString()} Meters", style = TextStyle(
                            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )

                    StopwatchSegmentedButtons(
                        options = listOf("Current Session", "Laps Detail"),
                        selectedTabIndex = state.selectedTabIndex,
                        onClick = { newPageSelected ->
                            onAction(StopwatchAction.OnTabSelected(newPageSelected))
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                }
                when (state.selectedTabIndex) {
                    0 -> {
                        StopwatchDisplay(
                            minutes = state.minutes,
                            seconds = state.seconds,
                            centiseconds = state.centiseconds,
                            stopwatchState = state.stopwatchState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(weight = 2f)
                        )
                        StopwatchButtons(
                            stopwatchState = state.stopwatchState,
                            onAction = onAction,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(weight = 2f)
                        )
                        if (state.session != null && state.session!!.laps.isNotEmpty()) {
                            Column(
                                modifier = Modifier
                                    .weight(0.5f)
                            ) {
                                val totalLapTime = state.session?.laps?.sumOf { it.totalTime }
                                val averageLapTime = totalLapTime?.div(state.session!!.laps.size)?.toLapString()
                                Text(
                                    text = "Average time/lap:  ${averageLapTime}"
                                )
                            }
                            LapsList(
                                laps = state.session!!.laps,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(weight = 4f)
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(weight = 4.5f))
                        }
                    }

                    1 -> {
                        LapsDetailChart(
                            state.session!!.laps,
                            modifier = Modifier.weight(2f)
                        )
                    }
                }

            }
        }
    }
    if (state.isConfirmCancelSessionDialogVisible) {
        CustomConfirmDialog(
            dialogTitle = stringResource(R.string.confirm_cancel_session),
            dialogSubTitle = state.confirmMessage?.asString(ctx)!!,
            onDismissRequest = { onAction(StopwatchAction.OnConfirmDialogDismiss) },
            onConfirmation = {
                onAction(StopwatchAction.OnConfirmDialogConfirm)
            }
        )
    }
    if (state.isConfirmSaveSessionDialogVisible) {
        CustomConfirmDialog(
            dialogTitle = stringResource(R.string.confirm_save_session),
            dialogSubTitle = state.confirmMessage?.asString(ctx)!!,
            onDismissRequest = { onAction(StopwatchAction.OnConfirmDialogDismiss) },
            onConfirmation = {
                onAction(StopwatchAction.OnConfirmDialogConfirm)
            }
        )
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
        stopwatchState = StopwatchState.Idle,
        player = Player(
            fullname = "Piero Silvestri",
            sessions = null,
            pictures = null,
            id = "1",
        ),
        session = Session(
            distance = 1000,
            laps = listOf(
                Lap(60, 50L),
                Lap(1000, 51L),
            ),
            startDate = 0L,
            id = "1",
        ),
        selectedTabIndex = 0
    )

    StopwatchScreen(
        state = state,
        onAction = {}
    )
}