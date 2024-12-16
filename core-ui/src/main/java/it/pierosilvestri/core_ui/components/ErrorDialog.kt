package it.pierosilvestri.core_ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import it.pierosilvestri.core.util.UiText
import it.pierosilvestri.core_ui.R

@Composable
fun ErrorDialog(
    errorMessage: UiText,
    onDismissRequest: () -> Unit,
) {
    val context = LocalContext.current
    AlertDialog(
        title = {
            Text(text = stringResource(R.string.error_dialog_title))
        },
        text = {
            Text(text = errorMessage.asString(context))
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(stringResource(it.pierosilvestri.core.R.string.custom_dialog_positive_button))
            }
        },
    )
}