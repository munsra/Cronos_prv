package it.pierosilvestri.stopwatch_domain.services

import it.pierosilvestri.stopwatch_domain.utils.StopwatchState

/**
 * This is the interface of my stopwatch service which will be implemented by the
 * module that will provide the implementation.
 */
interface StopwatchService {
    fun observeTime(callback: (String, String, String, StopwatchState) -> Unit)
    fun startStopwatch()
    fun stopStopwatch()
    fun resetStopwatch()
}