package it.pierosilvestri.stopwatch_presentation.stopwatch.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import it.pierosilvestri.core.domain.model.Lap
import it.pierosilvestri.stopwatch_domain.utils.StopwatchState
import it.pierosilvestri.stopwatch_presentation.stopwatch.mapper.toLapString

@Composable
fun LapsList(
    modifier: Modifier = Modifier,
    laps: List<Lap>,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            LazyColumn {
                itemsIndexed(laps.sortedByDescending { it.datetime }) { index, lap ->
                    Text(
                        text = "Lap ${laps.size - index} : ${lap.totalTime.toLapString()}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                }
            }
        }
    }

}