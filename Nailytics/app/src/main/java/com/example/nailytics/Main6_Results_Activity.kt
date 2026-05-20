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

        // Gets the currently selected analyte name from AnalyteChartUIHelper.
        // Examples: "pH", "Glucose", or "Nitrate".
        val selectedAnalyteName = AnalyteChartUIHelper.selectedAnalyteValueName

        // Extracts the analyte type from the selected label.
        // Examples: "pH 5" -> "pH", "Glucose 40 mM" -> "Glucose", "Nitrate 2.5 mM" -> "Nitrate".
        val analyte = listOf("pH", "Glucose", "Nitrate").firstOrNull{
            selectedAnalyteName.contains(it, ignoreCase = true) } ?: "Unknown"

        // Shows interpretation text for the selected analyte's measured value.
        when (analyte) {

            // If the selected analyte is pH, display pH-specific result text.
            "pH" -> {
                displayPHInterpretation(closestLabel)
            }

            // If the selected analyte is glucose, display glucose-specific result text.
            "Glucose" -> {
                displayGlucoseInterpretation(closestLabel)
            }

            // If the selected analyte is nitrate, display nitrate-specific result text.
            "Nitrate" -> {
                displayNitrateInterpretation(closestLabel)
            }

            // If the selected analyte name is unknown or unsupported,
            // show a fallback result instead of crashing.
            else -> {
                findViewById<TextView>(R.id.analyte_result).text = "$closestLabel: Unknown Analyte"

                findViewById<TextView>(R.id.analyte_result_description).text = "The measured analyte value could not be interpreted."
            }
        }

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
                resultTitle = "pH 4-: Highly Acidic"

                resultDescription =
                    "Your pH level is more acidic than the typical healthy range. " +
                    "This may be due to recent food or drink, hydration, or oral bacteria activity."
            }

            "pH 5" -> {
                resultTitle = "pH 5: Acidic"

                resultDescription =
                    "Your pH level appears more acidic than the typical healthy range. " +
                    "This may be due to recent food or drink, hydration, or oral bacteria activity."
            }

            "pH 6" -> {
                resultTitle = "pH 6: Slightly Acidic"

                resultDescription =
                    "Your pH level appears slightly acidic. Mild fluctuations like this is common " +
                     "and may vary with diet, hydration, or recent meals."
            }

            "pH 7" -> {
                resultTitle = "pH 7: Neutral"

                resultDescription =
                    "Your pH level is within a typical neutral range."
            }

            "pH 8" -> {
                resultTitle = "pH 8: Slightly Alkaline"

                resultDescription =
                "Your pH level appears slightly alkaline. Mild alkalinity is generally not uncommon " +
                "and may be due to hydration, diet, antacid use, sample type, or temporary biological variation."
            }

            "HIGH" -> {
                resultTitle = "pH 9+: Highly Alkaline"

                resultDescription =
                    "Your pH level appears more alkaline than the typical healthy range. " +
                    "This may be due to bacterial activity, supplements, or antacid use."

            }

            else -> {
                resultTitle = "$analyteValue: Unknown"

                resultDescription =
                    "The measured analyte value could not be interpreted."
            }
        }

        // Displays the final result title.
        findViewById<TextView>(R.id.analyte_result).text = resultTitle

        // Displays the explanation/interpretation.
        findViewById<TextView>(R.id.analyte_result_description).text = resultDescription
    }

    // Displays interpretation text for the measured glucose value.
    private fun displayGlucoseInterpretation(
        analyteValue: String
    ) {
        // Stores the title shown at the top.
        var resultTitle = ""

        // Stores the explanation text shown underneath.
        var resultDescription = ""

        // Chooses interpretation based on measured glucose value.
        when (analyteValue) {

            "LOW" -> {
                resultTitle = "Glucose < 40 mM: Very Low"

                resultDescription =
                    "Your glucose level appears below the typical healthy range. " +
                    "This may be due to prolonged fasting, increased exercise, glucose-lowering medication, or alcohol use."
            }

            "Glucose 40 mM" -> {
                resultTitle = "Glucose 40 mM: Low"

                resultDescription =
                    "Your glucose level appears near the lower end of the typical range. " +
                    "This may be due to physical activity or temporary changes in glucose regulation or hydration."
            }

            "Glucose 80 mM" -> {
                resultTitle = "Glucose 80 mM: Normal"

                resultDescription =
                    "Your glucose level appears within a typical healthy range. " +
                    "This is commonly associated with balanced glucose intake & regulation."
            }

            "Glucose 120 mM" -> {
                resultTitle = "Glucose 120 mM: Slightly Elevated"

                resultDescription =
                    "Your glucose level appears slightly above the typical range. " +
                    "This may be due to recent food or drink intake, reduced activity, or stress."
            }

            "Glucose 160 mM" -> {
                resultTitle = "Glucose 160 mM: Elevated"

                resultDescription =
                    "Your glucose level appears elevated compared with the typical range. " +
                    "This may be due to recent food or drink intake, glucose regulation changes, reduced activity, stress,or  illness."
            }

            "HIGH" -> {
                resultTitle = "Glucose > 160 mM: Very High"

                resultDescription =
                    "Your glucose level appears above the typical healthy range. " +
                    "This may be due to recent food or drink intake, glucose regulation changes, medication effects, stress, or illness."
            }

            else -> {
                resultTitle = "$analyteValue: Unknown"

                resultDescription =
                    "The measured glucose value could not be interpreted."
            }
        }

        // Displays the final result title.
        findViewById<TextView>(R.id.analyte_result).text = resultTitle

        // Displays the explanation/interpretation.
        findViewById<TextView>(R.id.analyte_result_description).text = resultDescription
    }

    // Displays interpretation text for the measured nitrate value.
    private fun displayNitrateInterpretation(
        analyteValue: String
    ) {
        // Stores the title shown at the top.
        var resultTitle = ""

        // Stores the explanation text shown underneath.
        var resultDescription = ""

        // Chooses interpretation based on measured nitrate value.
        when (analyteValue) {

            "LOW" -> {
                resultTitle = "Nitrate < 2.5 mM: Very Low"

                resultDescription =
                    "Your nitrate level appears below the typical range. " +
                    "This may be due to low intake of nitrate-rich foods, hydration, sample type, or temporary biological variation."
            }

            "Nitrate 2.5 mM" -> {
                resultTitle = "Nitrate 2.5 mM: Low"

                resultDescription =
                    "Your nitrate level appears near the lower end of the typical range. " +
                    "This may be due to diet, hydration, sample type, or temporary biological variation."
            }

            "Nitrate 5.0 mM" -> {
                resultTitle = "Nitrate 5.0 mM: Moderate"

                resultDescription =
                    "Your nitrate level appears within a typical moderate range. "
            }

            "Nitrate 7.5 mM" -> {
                resultTitle = "Nitrate 7.5 mM: Slightly Elevated"

                resultDescription =
                    "Your nitrate level appears slightly elevated within the typical range. " +
                    "This may be due to recent nitrate-rich foods, sample type, or temporary biological variation."
            }

            "Nitrate 10.0 mM" -> {
                resultTitle = "Nitrate 10.0 mM: Elevated"

                resultDescription =
                    "Your nitrate level appears near the higher end of the typical range. " +
                    "This may be due to recent nitrate-rich foods, oral microbiome activity, hydration, or temporary biological variation."
            }

            "HIGH" -> {
                resultTitle = "Nitrate > 10.0 mM: Very High"

                resultDescription =
                    "Your nitrate level appears above the typical range. " +
                    "This may be due to nitrate-rich foods, supplements, sample type, or temporary biological variation."
            }

            else -> {
                resultTitle = "$analyteValue: Unknown"

                resultDescription =
                    "The measured nitrate value could not be interpreted."
            }
        }

        // Displays the final result title.
        findViewById<TextView>(R.id.analyte_result).text = resultTitle

        // Displays the explanation/interpretation.
        findViewById<TextView>(R.id.analyte_result_description).text = resultDescription
    }

}