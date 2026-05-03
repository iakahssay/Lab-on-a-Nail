package com.example.nailytics

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Summary_Pattern_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start with the first screen
        setContentView(R.layout.summary_pattern)

        //Listeners for the (navigation) tab bar
        NavBar_Helper.moveToHome(this)
        NavBar_Helper.moveToProfile(this)
        NavBar_Helper.moveToSummary(this)
        //For the back button on the top of the screen
        NavBar_Helper.moveToPreviousScreen(this)
    }

}
