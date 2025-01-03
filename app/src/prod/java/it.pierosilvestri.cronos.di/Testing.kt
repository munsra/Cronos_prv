package it.pierosilvestri.cronos.di

import android.util.Log

class TestingImpl: Testing {
    override fun logMessage() {
        Log.d("TESTING", "PROD Flavor Message")
    }
}