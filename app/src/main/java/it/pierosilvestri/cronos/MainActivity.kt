package it.pierosilvestri.cronos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import it.pierosilvestri.calorytracker.navigation.Navigator
import it.pierosilvestri.core_ui.theme.CronosTheme
import it.pierosilvestri.cronos.di.Testing
import it.pierosilvestri.cronos.di.TestingImpl

class MainActivity : ComponentActivity() {

    private lateinit var testing: Testing

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        testing = provideTesting()
        testing.logMessage()
        setContent {
            CronosTheme {
                Navigator()
            }
        }
    }

    private fun provideTesting(): Testing {
        // Each flavor provides its own implementation of Testing
        return TestingImpl()
    }
}