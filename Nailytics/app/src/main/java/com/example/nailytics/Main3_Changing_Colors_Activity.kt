package com.example.nailytics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View

class Main3_Changing_Colors_Activity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start with your first exported screen
        setContentView(R.layout.main3_changing_colors)


        //Shows the selected/update analyte color chart
        AnalyteChartUIHelper.updateColorChart(this)

        //Show the dropdown menu and the dimming overlay feature that comes with it
        findViewById<View>(R.id.dropDownSection).setOnClickListener {
            AnalyteChartUIHelper.showAnalyteDropdown(this, it)
        }

        //This screen is a bit unique as it doesn't have a 'next' screen. Instead, its more like a pop up sacreen, where it
        // saves the color change, then goes back to original screen
        moveToPreviousScreen() //Listener(s) that will move this screen (main2) back to whichever screen called on it first

        //Listeners for the (navigation) tab bar
        NavBar_Helper.moveToHome(this)
        NavBar_Helper.moveToProfile(this)
        NavBar_Helper.moveToSummary(this)
    }


    //1) PAGE FLOW FUNCTION
    private fun moveToPreviousScreen() {
        //Sets the shared onClickListener for multiple buttons
        val goBack  = View.OnClickListener{
            finish()
        }

        //Implements that listener for the specific buttons we want
        findViewById<View>(R.id.back_button).setOnClickListener(goBack)
        findViewById<View>(R.id.cancel_button).setOnClickListener(goBack)
        findViewById<View>(R.id.save_button).setOnClickListener(goBack)
    }

}