package it.pierosilvestri.stopwatch_presentation.stopwatch

sealed class StopwatchEvent {
    data object NavigateBack : StopwatchEvent()
}