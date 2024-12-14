package it.pierosilvestri.stopwatch_presentation.stopwatch.mapper

/**
 * Convert a Long centi seconds to a String with the format mm:ss:cs.
 */
fun Long.toLapString(): String {
    val minutes = (this / 6000).toInt()
    val seconds = ((this % 6000) / 100).toInt()
    val remainingCentiseconds = (this % 100).toInt()

    return "${minutes.toString().padStart(2, '0')} : ${
        seconds.toString().padStart(2, '0')
    } : ${remainingCentiseconds.toString().padStart(2, '0')}"
}