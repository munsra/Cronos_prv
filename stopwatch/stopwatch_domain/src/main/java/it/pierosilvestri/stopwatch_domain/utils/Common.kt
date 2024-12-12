package it.pierosilvestri.stopwatch_domain.utils

fun formatTime(seconds: String, minutes: String, hours: String): String {
    return "$hours:$minutes:$seconds"
}

fun Int.pad(): String {
    return this.toString().padStart(2, '0')
}

fun Int.twoDigits(): String {
    return this.toString().padStart(2, '0')
}

fun Long.twoDigits(): String {
    return this.toString().padStart(2, '0')
}

fun String.twoChars(): String {
    return this.padStart(2, '0')
}

fun Int.nanosecondToCentisecondToTwoDigits(): String {
    return (this / 10_000_000).toString().padStart(2, '0')
}