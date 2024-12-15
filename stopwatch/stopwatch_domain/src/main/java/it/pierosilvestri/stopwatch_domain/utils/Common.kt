package it.pierosilvestri.stopwatch_domain.utils

/**
 * Convert an integer to two digits string.
 */
fun Int.twoDigits(): String {
    return this.toString().padStart(2, '0')
}

/**
 * Convert a Long to two digits string.
 */
fun Long.twoDigits(): String {
    return this.toString().padStart(2, '0')
}

/**
 * Convert a String to two digits string.
 */
fun String.twoChars(): String {
    return this.padStart(2, '0')
}

/**
 * Convert nanoseconds to centisecond and then to a two digits string.
 */
fun Int.nanosecondToCentisecondToTwoDigits(): String {
    return (this / 10_000_000).toString().padStart(2, '0')
}