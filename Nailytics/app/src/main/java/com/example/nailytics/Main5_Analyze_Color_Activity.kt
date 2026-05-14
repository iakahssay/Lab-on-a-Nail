package com.example.nailytics

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Main5_Analyze_Color_Activity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start with your first exported screen
        setContentView(R.layout.main5_analyze_color)

        //Shows the selected/update analyte color chart
        AnalyteChartUIHelper.updateColorChart(this)

        //Show the dropdown menu and the dimming overlay feature that comes with it
        findViewById<View>(R.id.dropDownSection).setOnClickListener {
            AnalyteChartUIHelper.showAnalyteDropdown(this, it)
        }

        //Listener for editing analyte's color (does that in main2 screen)
        moveToMain3()

        //Listener that will move this screen (main5) to the next (main6)
        moveToMain6()

        //Listener for changing Nix device (moves back to main2)
        changeNixDevice()

        //Listeners for the (navigation) tab bar
        NavBar_Helper.moveToHome(this)
        NavBar_Helper.moveToProfile(this)
        NavBar_Helper.moveToSummary(this)
        //For the back button on the top of the screen
        NavBar_Helper.moveToPreviousScreen(this)
    }

    override fun onResume() {
        super.onResume()

        // Refreshes the chart whenever this screen becomes visible again.
        // This is important after returning from Main3 using finish() (ie after returning from editing the analyte's color)
        AnalyteChartUIHelper.updateColorChart(this)
    }

//1) PAGE FLOW FUNCTIONS
    private fun moveToMain3() {
        findViewById<View>(R.id.edit_color_button).setOnClickListener {
            startActivity(Intent(this, Main3_Changing_Colors_Activity::class.java))
        }
    }

    private fun moveToMain6() {
        findViewById<View>(R.id.analyze_button).setOnClickListener {
            startActivity(Intent(this, Main6_Results_Activity::class.java))
        }
    }

    private fun changeNixDevice(){
        findViewById<View>(R.id.change_device).setOnClickListener {
            startActivity(Intent(this, Main2_Searching_For_Nix_Activity::class.java))
        }
    }

}