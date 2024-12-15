package it.pierosilvestri.core_ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import it.pierosilvestri.core.R

@Composable
fun CustomConfirmDialog(
    dialogTitle: String,
    dialogSubTitle: String? = null,
    onDismissRequest: () -> Unit,
    confirmButton: @Composable (() -> Unit)? = null,
    dismissButton: @Composable (() -> Unit)? = null,
    content: @Composable (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            if (confirmButton == null) {
                TextButton(onClick = { onDismissRequest() }) {
                    Text(text = stringResource(R.string.custom_dialog_positive_button))
                }
            } else {
                confirmButton()
            }
        },
        dismissButton = {
            if(dismissButton == null){
                TextButton(onClick = { onDismissRequest() }) {
                    Text(text = "Cancel")
                }
            } else {
                dismissButton()
            }
        },
        title = {
            Text(text = dialogTitle, fontSize = 18.sp)
        },
        text = {
            if (content == null && dialogSubTitle != null) {
                Text(text = dialogSubTitle)
            }
            if (content != null) {
                content()
            }
        })
}

@Composable
fun CustomConfirmDialog(
    dialogTitle: String,
    dialogSubTitle: String,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogSubTitle)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(stringResource(R.string.custom_dialog_positive_button))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(stringResource(R.string.custom_dialog_negative_button))
            }
        }
    )
}