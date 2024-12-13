package it.pierosilvestri.stopwatch_presentation.stopwatch

sealed class StopwatchAction {
    data object OnStart: StopwatchAction()
    data object OnStop: StopwatchAction()
    data object OnReset: StopwatchAction()
    data object OnResume: StopwatchAction()
}