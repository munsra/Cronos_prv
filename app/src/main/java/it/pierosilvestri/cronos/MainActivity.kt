package it.pierosilvestri.cronos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import it.pierosilvestri.calorytracker.navigation.Navigator
import it.pierosilvestri.core_ui.theme.CronosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CronosTheme {
                Navigator()
            }
        }
    }
}