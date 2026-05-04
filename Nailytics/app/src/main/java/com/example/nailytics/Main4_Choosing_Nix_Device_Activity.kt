package com.example.nailytics

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Main4_Choosing_Nix_Device_Activity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start with your first exported screen
        setContentView(R.layout.main4_choosing_nix_device)

        //Shows the selected/update analyte color chart
        AnalyteChartUIHelper.updateColorChart(this)

        //Show the dropdown menu and the dimming overlay feature that comes with it
        findViewById<View>(R.id.dropDownSection).setOnClickListener {
            AnalyteChartUIHelper.showAnalyteDropdown(this, it)
        }

        //Listener for editing analyte's color (does that in main2 screen)
        moveToMain3()

        //Listener that will move this screen (main1) to the next (main4)
        moveToMain5()

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

    private fun moveToMain5() {
        //Sets the shared onClickListener for multiple buttons
        val goToMain5 = View.OnClickListener {
            val intent = Intent(this, Main5_Analyze_Color_Activity::class.java)
            startActivity(intent)
        }

        //Implements that listener for the specific buttons we want
        findViewById<View>(R.id.device1_button).setOnClickListener(goToMain5)
        findViewById<View>(R.id.device2_button).setOnClickListener(goToMain5)
    }

}