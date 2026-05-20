package com.example.nailytics

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts // Modern Android permission request system.
import com.nixsensor.universalsdk.IDeviceCompat


class Main2_Searching_For_Nix_Activity : AppCompatActivity() {
     // Handles the result AFTER the user responds to the Bluetooth permission popup.
     // Launches the Android runtime permission popup for Bluetooth permissions.
     // "ActivityResultContracts.RequestMultiplePermissions()" -> Registers a permission request handler that can request multiple permissions at once.
     private val nixPermissionLauncher = registerForActivityResult(
         ActivityResultContracts.RequestMultiplePermissions()
     ) { permissions ->
         // Checks whether every requested permission was approved.
         val allGranted = permissions.values.all { it }

         if (allGranted) {
             // If permissions were approved, search for and connect to a Nix device.
             startNixSearchAndConnect()
         } else {
             // If permissions were denied, tell the user why they are needed.
             Toast.makeText(
                 this,
                 "Bluetooth permissions are required to connect to a Nix device.",
                 Toast.LENGTH_LONG
             ).show()
         }
     }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Loads the Main2 searching-for-Nix screen.
        setContentView(R.layout.main2_searching_for_nix)

        //Shows the selected/update analyte color chart
        AnalyteChartUIHelper.updateColorChart(this)

        //Show the dropdown menu and the dimming overlay feature that comes with it
        findViewById<View>(R.id.dropDownSection).setOnClickListener {
            AnalyteChartUIHelper.showAnalyteDropdown(this, it)
        }

        // Starts the Nix permission + scan + connect flow.
        startNixPermissionFlow()

        //Listener for editing analyte's color (does that in main2 screen)
        moveToMain3()

        //Listeners for the (navigation) tab bar
        NavBar_Helper.moveToHome(this)
        NavBar_Helper.moveToProfile(this)
        NavBar_Helper.moveToSummary(this)
        //For the back button on the top of the screen
        moveToPreviousScreen()
    }

     override fun onResume() {
         super.onResume()

         // Refreshes the chart whenever this screen becomes visible again.
         // This is important after returning from Main3 using finish() (ie after returning from editing the analyte's color)
         AnalyteChartUIHelper.updateColorChart(this)
     }

     override fun onDestroy() {
         super.onDestroy()

         // Stops scanning if the user leaves Main2 before a device connects.
         NixSensorManager.stopScan()
     }

//1) PAGE FLOW FUNCTIONS
    private fun moveToMain3() {
        findViewById<View>(R.id.edit_color_button).setOnClickListener {
            startActivity(Intent(this, Main3_Changing_Colors_Activity::class.java))
        }
    }

    //Once connected to a Nix device, this function will be called on to move to next screen (Main5)
    private fun moveToMain5(nixDevice: IDeviceCompat){
         // MOVE DIRECTLY TO MAIN5 AFTER CONNECTION SUCCEEDS
         val intent = Intent(this, Main5_Analyze_Color_Activity::class.java)

         // Pass device info to Main5 for display if needed.
         intent.putExtra("device_name", nixDevice.name)
         intent.putExtra("device_id", nixDevice.id)

         startActivity(intent) //Go to Main5

         // Remove Main2 from the back stack after successful connection
         // This way, after the Nix connects successfully, the app moves forward
         // to Main5 and cannot accidentally fall back to the searching screen. (found this bug during testing)
         finish()
     }

    private fun moveToPreviousScreen() {
        findViewById<View>(R.id.back_button).setOnClickListener {
            // Stop searching and disconnect from Nix because the user is leaving
            // the Nix connection flow and returning to the home screen.
            NixSensorManager.disconnect()

            val intent = Intent(this, Main1_Activity::class.java)

            // Return to the existing Main1 screen if it is already in the back stack,
            // clear any screens above it, and avoid creating a duplicate Main1.
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

            startActivity(intent)
            finish()
        }
    }

//2) NIX SENSING HELPER FUNCTIONS
// Checks Bluetooth permissions, then starts Nix scanning.
 private fun startNixPermissionFlow() {
     if (NixSensorManager.hasBluetoothPermissions(this)) {  // If permissions already exist:
         startNixSearchAndConnect()  // Begin scanning/connecting immediately.
     } else {
         //Otherwise, ask the user for the Bluetooth permissions required by the Nix SDK.
         nixPermissionLauncher.launch( NixSensorManager.requiredBluetoothPermissions() )
     }
 }

     // Shows loading UI while searching/connecting.
     private fun showNixLoadingState() {
         // Shows the animated spinner.
         findViewById<View>(R.id.loading_spinner).visibility = View.VISIBLE
     }

     // Hides loading UI after searching/connecting finishes.
     private fun hideNixLoadingState() {
         // Hides the animated spinner.
         findViewById<View>(R.id.loading_spinner).visibility = View.GONE
     }

     // Searches for the first nearby Nix device and connects automatically.
     private fun startNixSearchAndConnect() {
         // Show spinner immediately.
         showNixLoadingState()

         // Clear any stale previous Nix connection before starting a new scan.
         NixSensorManager.disconnect()

         // Ask NixSensorManager to scan and auto-connect.
         NixSensorManager.startScan(
             context = this,

             // Runs when scanning starts.
             onScanStarted = {
                 runOnUiThread {
                     showNixLoadingState()

                     Toast.makeText(
                         this,
                         "Searching for Nix device...",
                         Toast.LENGTH_SHORT
                     ).show()
                 }
             },

             // Runs when a nearby Nix device is found.
             onDeviceFound = { device ->
                 runOnUiThread {
                     Toast.makeText(
                         this,
                         "Found ${device.name}. Connecting...",
                         Toast.LENGTH_LONG
                     ).show()
                 }
             },

             // Runs when the Nix device successfully connects.
             onConnected = { device ->
                 runOnUiThread {
                     hideNixLoadingState()

                     Toast.makeText(
                         this,
                         "Connected to ${device.name}",
                         Toast.LENGTH_LONG
                     ).show()

                     // MOVE DIRECTLY TO MAIN5 AFTER CONNECTION SUCCEEDS
                     moveToMain5(device)
                 }
             },

             // Runs if the device disconnects.
             onDisconnected = { device, status ->
                 runOnUiThread {
                     Toast.makeText(
                         this,
                         "${device.name} disconnected: $status",
                         Toast.LENGTH_LONG
                     ).show()

                     hideNixLoadingState()
                 }
             },

             // Runs if scanning or connection fails.
             onError = { errorMessage ->
                 runOnUiThread {
                     hideNixLoadingState()

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