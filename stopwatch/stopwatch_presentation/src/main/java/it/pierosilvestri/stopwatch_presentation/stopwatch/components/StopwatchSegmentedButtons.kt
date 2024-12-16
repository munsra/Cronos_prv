package it.pierosilvestri.stopwatch_presentation.stopwatch.components

import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text

@Composable
fun StopwatchSegmentedButtons(
    onClick: (Int) -> Unit,
    selectedTabIndex: Int,
    options: List<String>,
    modifier: Modifier = Modifier
) {
    SingleChoiceSegmentedButtonRow(
        modifier = modifier
    ) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = {
                    onClick(index)
                },
                selected = index == selectedTabIndex,
            ) {
                Text(label)
            }
        }
    }
}