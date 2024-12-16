package it.pierosilvestri.stopwatch_presentation.stopwatch.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.Line
import it.pierosilvestri.core.domain.model.Lap

@Composable
fun LapsDetailChart(
    laps: List<Lap>,
    modifier: Modifier = Modifier
) {
    val data by remember {
        mutableStateOf(
            listOf(
                Line(
                    label = "Laps",
                    values = laps.map {
                        it.totalTime.toDouble() / 100
                    },
                    color = SolidColor(Color.Red),
                ),
            )
        )
    }
    LineChart(
        modifier = modifier,
        data = data,
        curvedEdges = false,
    )
}