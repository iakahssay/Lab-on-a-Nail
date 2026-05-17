package com.example.nailytics

import android.app.Application
import android.util.Log
import com.example.nailytics.BuildConfig
import com.nixsensor.universalsdk.LicenseManager
import com.nixsensor.universalsdk.LicenseManagerState
import kotlin.concurrent.thread

//GLOBAL STARTUP CLASS
//-> guarantees the NIX SDK is set up (or initialized) even if the app later opens from a
// different Activity, deep link, notification, or testing path.
class NailyticsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Runs Nix license activation in the background so app
        // startup doesn't freeze the UI thread.
        thread {
            activateNixLicense()
        }
    }

    //Turn on the Nix SDK license when the app starts.
    private fun activateNixLicense() {
        val options = BuildConfig.NIX_LICENSE_OPTIONS
        val signature = BuildConfig.NIX_LICENSE_SIGNATURE

        val activationState = LicenseManager.activate(
            context = applicationContext,
            options = options,
            signature = signature
        )

        if (activationState == LicenseManagerState.ACTIVE) {
            Log.d("NixLicense", "Nix SDK license activated successfully")
        } else {
            Log.e("NixLicense", "Nix SDK license activation failed: $activationState")
        }
    }
}