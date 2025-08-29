package com.rahul.auric.auricscan.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.rahul.auric.auricscan.MainActivity

/*
 * This is the app's starting point.
 * On modern Android, this Activity doesn't show its own UI.
 * Instead, the system shows the splash screen defined in themes.xml.
 * This Activity's only job is to prepare the app and then launch MainActivity.
 */
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // This is the crucial call that connects this Activity to the system's splash screen.
        // It must be called before super.onCreate().
        installSplashScreen()

        super.onCreate(savedInstanceState)

        // After the system splash screen is done, we immediately navigate
        // to the main part of the app.
        startActivity(Intent(this, MainActivity::class.java))

        // We call finish() so the user can't press the back button to return to this screen.
        finish()
    }
}