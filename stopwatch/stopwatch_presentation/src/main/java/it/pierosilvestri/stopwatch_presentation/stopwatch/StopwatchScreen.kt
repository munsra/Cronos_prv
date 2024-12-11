package it.pierosilvestri.stopwatch_presentation.stopwatch

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun StopwatchScreenRoot(
    modifier: Modifier = Modifier
) {
    StopwatchScreen()
}


@Composable
private fun StopwatchScreen(){
    Scaffold {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ){
            Text("Stopwatch")
        }
    }
}

@Preview
@Composable
fun PreviewStopwatchScreen(){
    StopwatchScreen()
}