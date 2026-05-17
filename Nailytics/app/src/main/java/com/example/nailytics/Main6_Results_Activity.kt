package com.example.nailytics

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.graphics.Typeface
import android.widget.LinearLayout

class Main6_Results_Activity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Loads the Main 5 Results screen.
        setContentView(R.layout.main_results)

        //Shows the selected/update analyte color chart
        AnalyteChartUIHelper.updateColorChart(this)

        showConnectedDeviceName()

        // Displays the calculated analyte result from Main5.
        displayAnalyteResult()

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


//1) PAGE FLOW FUNCTIONS
    // If "Change Device" link is pressed, send user back to Main2
    // to search/connect to a different Nix device.
    private fun changeNixDevice() {
        findViewById<View>(R.id.change_device).setOnClickListener {

            // Disconnects the current Nix device before searching again.
            NixSensorManager.disconnect()

            // Send user to Main 2
            startActivity(Intent(this, Main2_Searching_For_Nix_Activity::class.java))
        }
    }

//2) SHOWCASING NIX RESULTS (UI) SECTION
    // Displays the connected Nix device name on Main5.
    private fun showConnectedDeviceName() {
        // Gets the device name passed from Main2.
        val deviceName = intent.getStringExtra("device_name")

        // Updates the connected-device label if a name exists.
        if (!deviceName.isNullOrBlank()) {
            findViewById<TextView>(R.id.nix_connected).text = "$deviceName connected. "
        }
    }

    // Reads measurement/matching result from Main5 and displays it on Main6.
    private fun displayAnalyteResult() {

        // Gets the measured color from Main5.
        val measuredColor = intent.getIntExtra("measured_color", Color.TRANSPARENT)

        // Shows the measured Nix color in a color swatch.
        findViewById<LinearLayout?>(R.id.analyte_result_color)?.setBackgroundColor(measuredColor)

        // Gets the closest analyte value label from Main5.
        // Ex: "pH 5", "40 mM", "2.5 mM"
        val closestLabel = intent.getStringExtra("closest_label") ?: "Unknown"

        // Shows interpretation text for the measured analyte value.
        displayPHInterpretation(closestLabel)

        // Gets the closest chart color from Main5.
        //val closestColor = intent.getIntExtra("closest_color", Color.TRANSPARENT)

        // Shows the closest matched analyte chart color in a color swatch.
        //findViewById<LinearLayout?>(R.id.analyte_result_color)?.setBackgroundColor(closestColor)

    }

    // Displays interpretation text for the measured pH value.
    private fun displayPHInterpretation(
        analyteValue: String
    ) {
        // Stores the title shown at the top.
        var resultTitle = ""

        // Stores the explanation text shown underneath.
        var resultDescription = ""

        // Chooses interpretation based on measured analyte value.
        when (analyteValue) {

            "LOW" -> {
                resultTitle = "pH 4- - Highly Acidic"

                resultDescription =
                    "Your saliva is more acidic than the typical healthy range. " +
                    "This can occur temporarily due to recent food or drink, dehydration, stress, or acid reflux."
            }

            "pH 5" -> {
                resultTitle = "pH 5 - Acidic"

                resultDescription =
                    "Your saliva appears more acidic than the typical healthy range. " +
                    "This can occur temporarily due to recent food or drink, dehydration, stress, or oral bacteria activity."
            }

            "pH 6" -> {
                resultTitle = "pH 6 - Slightly Acidic"

                resultDescription =
                    "Your saliva appears slightly acidic. Mild fluctuations throughout the day are " +
                    "common and may be influenced by diet, hydration, or recent meals."
            }

            "pH 7" -> {
                resultTitle = "pH 7 - Neutral"

                resultDescription =
                    "Your saliva is within a typical neutral range, which is commonly associated with " +
                    "a balanced oral environment and healthy salivary conditions."
            }

            "pH 8" -> {
                resultTitle = "pH 8 - Slightly Alkaline"

                resultDescription =
                    "Your saliva appears slightly alkaline. This may occur temporarily due to hydration, diet, " +
                    "antacid use, or natural variation in saliva composition. Mild alkalinity is generally not uncommon and may help reduce oral acidity."
            }

            "HIGH" -> {
                resultTitle = "pH 9+ - Highly Alkaline"

                resultDescription =
                    "Your saliva appears more alkaline than the typical healthy range. " +
                    "Elevated alkalinity may occur due to changes in saliva composition, bacterial activity, " +
                    "supplements or antacid use, or temporary oral environment shifts."
            }

            else -> {
                resultTitle = "$analyteValue - Unknown"

                resultDescription =
                    "The measured analyte value could not be interpreted."
            }
        }

        // Displays the final result title.
        findViewById<TextView>(R.id.analyte_result).text = resultTitle

        // Displays the explanation/interpretation.
        findViewById<TextView>(R.id.analyte_result_description).text = resultDescription
    }

}