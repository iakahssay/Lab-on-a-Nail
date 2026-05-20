package com.example.nailytics

import android.app.Activity
import android.content.Intent
import android.view.View

object NavBar_Helper {
    fun moveToHome(activity: Activity) {
        activity.findViewById<View?>(R.id.home_tab)?.setOnClickListener {
            // Stop searching and disconnect from Nix because the user is leaving
            // the Nix connection flow and returning to the home screen.
            NixSensorManager.disconnect()

            val intent = Intent(activity, Main1_Activity::class.java)

            // Return to the existing home screen (ie Main1) if it's already in the back stack,
            // clear any screens above it, and avoid creating a duplicate Main1.
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

            activity.startActivity(intent)
        }
    }

    fun moveToProfile(activity: Activity) {
        activity.findViewById<View?>(R.id.profile_tab)?.setOnClickListener {
            // Stop searching and disconnect from Nix because the user is leaving
            // the Nix connection flow and returning to the home screen.
            NixSensorManager.disconnect()

            activity.startActivity(Intent(activity, Profile1_Main_Activity::class.java))
        }
    }

    fun moveToSummary(activity: Activity) {
        activity.findViewById<View?>(R.id.summary_tab)?.setOnClickListener {
            // Stop searching and disconnect from Nix because the user is leaving
            // the Nix connection flow and returning to the home screen.
            NixSensorManager.disconnect()

            activity.startActivity(Intent(activity, Summary_Main_Activity::class.java))
        }
    }

    fun moveToPreviousScreen(activity: Activity){
        activity.findViewById<View?>(R.id.back_button)?.setOnClickListener {
            activity.finish()
        }
    }
}