package it.pierosilvestri.stopwatch_presentation.stopwatch

sealed class StopwatchAction {
    data object OnStart: StopwatchAction()
    data object OnStop: StopwatchAction()
    data object OnLap: StopwatchAction()
    data object OnReset: StopwatchAction()
    data object OnResume: StopwatchAction()
    data object OnBackButtonPressed: StopwatchAction()
    data object OnSaveButtonPressed: StopwatchAction()
    data object OnConfirmDialogDismiss: StopwatchAction()
    data object OnConfirmDialogConfirm: StopwatchAction()
    data class OnTabSelected(val pageIndex: Int): StopwatchAction()
}