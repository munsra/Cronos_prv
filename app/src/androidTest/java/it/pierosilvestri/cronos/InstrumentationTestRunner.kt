package it.pierosilvestri.cronos

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.test.runner.AndroidJUnitRunner

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class InstrumentationTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        classLoader: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(
            classLoader,
            CronosTestApp::class.java.name,
            context
        )
    }
}