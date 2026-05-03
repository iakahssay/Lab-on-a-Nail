package com.example.nailytics

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Summary_Main_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start with the first screen
        setContentView(R.layout.summary_main)

        //Show the dropdown menu and the dimming overlay feature that comes with it
        findViewById<View>(R.id.dropDownSection).setOnClickListener {
            AnalyteChartUIHelper.showAnalyteDropdown(this, it)
        }

        //Listener that will move this screen (main1) to the next (main4)
        moveToSummary2()

        //Listeners for the (navigation) tab bar
        NavBar_Helper.moveToHome(this)
        NavBar_Helper.moveToProfile(this)
        //For the back button on the top of the screen
        NavBar_Helper.moveToPreviousScreen(this)
    }


//1) PAGE FLOW FUNCTIONS
    private fun moveToSummary2(){
        findViewById<View>(R.id.summary_pattern_description).setOnClickListener {
            startActivity(Intent(this, Summary_Pattern_Activity::class.java))
        }
    }



}