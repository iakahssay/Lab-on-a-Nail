package com.example.nailytics

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.widget.TextView
import android.widget.Toast
import android.graphics.Typeface
import android.util.Log

class Main5_Analyze_Color_Activity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Loads the Main 5 Analyze Color screen.
        setContentView(R.layout.main5_analyze_color)

        //Shows the selected/update analyte color chart
        AnalyteChartUIHelper.updateColorChart(this)

        //Show the dropdown menu and the dimming overlay feature that comes with it
        findViewById<View>(R.id.dropDownSection).setOnClickListener {
            AnalyteChartUIHelper.showAnalyteDropdown(this, it)
        }

        // Shows the connected Nix device name if Main2 passed it through the Intent.
        showConnectedDeviceName()

        //Listener for editing analyte's color
        moveToMain3()

        // Uses the connected Nix device to measure color when Analyze is pressed.
        analyzeColorWithNix()

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

    // Opens Main6 and passes the Nix measurement result.
    private fun moveToMain6(result: NixColorMatchResult) {
        val intent = Intent(this, Main6_Results_Activity::class.java)

        // Pass device info (from Main 2) to Main6 for display if needed.
        intent.putExtra("device_name", getIntent().getStringExtra("device_name"))

        // Sends measured Nix color.
        intent.putExtra("measured_color", result.measuredColor)

        // Sends closest analyte value label.
        intent.putExtra("closest_label", result.closestLabel)

        // Sends closest chart color.
        intent.putExtra("closest_color", result.closestColor)

        startActivity(intent)
    }

    private fun changeNixDevice() {
        findViewById<View>(R.id.change_device).setOnClickListener {
            Log.d("Main5", "Change device clicked")

            // Disconnects the current device before searching again.
            NixSensorManager.disconnect()
            startActivity(Intent(this, Main2_Searching_For_Nix_Activity::class.java))
        }
    }

//2) NIX SENSOR FUNCTIONS
    // Displays the connected Nix device name on Main5.
    private fun showConnectedDeviceName() {
        // Gets the device name passed from Main2.
        val deviceName = intent.getStringExtra("device_name")

        // Updates the connected-device label if a name exists.
        if (!deviceName.isNullOrBlank()) {
            findViewById<TextView>(R.id.nix_connected).apply {

                // Sets the displayed device connection text.
                text = "$deviceName connected. "

                // Makes the entire string bold.
                typeface = Typeface.DEFAULT_BOLD
            }
        }
    }

    // Runs the Nix measurement + color matching flow once analyze_button is pressed
    private fun analyzeColorWithNix() {
        findViewById<View>(R.id.analyze_button).setOnClickListener {
            Log.d("Main5", "Analyze button clicked")

            // Tells the user measurement started.
            Toast.makeText(
                this,
                "Measuring color...",
                Toast.LENGTH_LONG
            ).show()

            // Calls the singleton object to measure and map the result.
            NixSensorManager.measureAndMatchCurrentAnalyte(

            //Passing in lambda function arguments
                // Runs if measurement + matching succeeds.
                onSuccess = { result ->
                    // Nix callbacks may not run on the UI thread, so switch back.
                    runOnUiThread {
                        Log.d("Main5", "Measurement success. Moving to Main6.")

                        // Shows the closest mapped analyte value.
                        Toast.makeText(
                            this,
                            "Measurement success",
                            Toast.LENGTH_LONG
                        ).show()

                        // Moves to the Results screen with the measured/matched values.
                        moveToMain6(result)
                    }
                },

                // Runs if measurement fails.
                onError = { errorMessage ->
                    // Switch back to UI thread before showing Toast.
                    runOnUiThread {
                        Log.d("Main5", "Measurement error: $errorMessage")

                        Toast.makeText(
                            this,
                            errorMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            )
        }
    }

}