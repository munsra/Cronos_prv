package it.pierosilvestri.leaderboard_presentation.leaderboard.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import it.pierosilvestri.core.util.UiText
import it.pierosilvestri.leaderboard_presentation.R

@Composable
fun LoadingDialog(
    modifier: Modifier = Modifier,
    message: UiText? = null
) {
    val context = LocalContext.current
    AlertDialog(
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        title = {
            Text(text = stringResource(R.string.loading))
        },
        text = {
            Column {
                if(message != null) Text(text = message.asString(context))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator()
                }
            }

        },
        onDismissRequest = {

        },
        confirmButton = {
        },
    )
}