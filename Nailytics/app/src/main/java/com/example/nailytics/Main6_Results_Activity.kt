package com.example.nailytics

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Main6_Results_Activity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start with your first exported screen
        setContentView(R.layout.main_results)

        //Shows the selected/update analyte color chart
        AnalyteChartUIHelper.updateColorChart(this)

        //Listener for changing Nix device (moves back to main2)
        changeNixDevice()

        //Listeners for the (navigation) tab bar
        NavBar_Helper.moveToHome(this)
        NavBar_Helper.moveToProfile(this)
        NavBar_Helper.moveToSummary(this)
        //For the back button on the top of the screen
        NavBar_Helper.moveToPreviousScreen(this)

        //Last Main Screen (no 'next' screen left)
    }

    private fun changeNixDevice(){
        findViewById<View>(R.id.change_device).setOnClickListener {
            startActivity(Intent(this, Main2_Searching_For_Nix_Activity::class.java))
        }
    }

}