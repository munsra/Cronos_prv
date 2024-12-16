package it.pierosilvestri.stopwatch_presentation.stopwatch.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import it.pierosilvestri.core_ui.theme.Blue
import it.pierosilvestri.stopwatch_domain.utils.StopwatchState

@Composable
fun StopwatchDisplay(
    minutes: String,
    seconds: String,
    centiseconds: String,
    stopwatchState: StopwatchState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${minutes}:", style = TextStyle(
                fontSize = MaterialTheme.typography.displayLarge.fontSize,
                fontWeight = FontWeight.Bold,
                color = if (stopwatchState != StopwatchState.Started) Color.Black else Blue
            )
        )
        Text(
            text = "${seconds}:", style = TextStyle(
                fontSize = MaterialTheme.typography.displayLarge.fontSize,
                fontWeight = FontWeight.Bold,
                color = if (stopwatchState != StopwatchState.Started) Color.Black else Blue
            )
        )
        Text(
            text = centiseconds, style = TextStyle(
                fontSize = MaterialTheme.typography.displayLarge.fontSize,
                fontWeight = FontWeight.Bold,
                color = if (stopwatchState != StopwatchState.Started) Color.Black else Blue
            )
        )
    }
}