package com.example.nailytics

import android.app.Activity
import android.content.Intent
import android.view.View

object NavBar_Helper {
    fun moveToHome(activity: Activity) {
        /*
        val intent = Intent(activity, Main1_Activity::class.java)
        activity.startActivity(intent)
        */

        activity.findViewById<View?>(R.id.home_tab)?.setOnClickListener {
            activity.startActivity(Intent(activity, Main1_Activity::class.java))
        }
    }

    fun moveToProfile(activity: Activity) {
        /*
       val intent = Intent(activity, Profile_Main_Activity::class.java)
       activity.startActivity(intent)
       */

        activity.findViewById<View?>(R.id.profile_tab)?.setOnClickListener {
            activity.startActivity(Intent(activity, Profile_Main_Activity::class.java))
        }
    }

    fun moveToSummary(activity: Activity) {
        /*
        val intent = Intent(activity, Summary_Main_Activity::class.java)
        activity.startActivity(intent)
        */

        activity.findViewById<View?>(R.id.summary_tab)?.setOnClickListener {
            activity.startActivity(Intent(activity, Summary_Main_Activity::class.java))
        }
    }

    fun moveToPreviousScreen(activity: Activity){
        activity.findViewById<View?>(R.id.back_button)?.setOnClickListener {
            activity.finish()
        }
    }
}