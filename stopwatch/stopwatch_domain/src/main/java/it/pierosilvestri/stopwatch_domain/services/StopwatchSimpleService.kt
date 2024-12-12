package it.pierosilvestri.stopwatch_domain.services

import it.pierosilvestri.stopwatch_domain.utils.StopwatchState

interface StopwatchSimpleService {
    fun observeTime(callback: (String, String, String, StopwatchState) -> Unit)
    fun startStopwatch()
    fun stopStopwatch()
    fun resetStopwatch()
}