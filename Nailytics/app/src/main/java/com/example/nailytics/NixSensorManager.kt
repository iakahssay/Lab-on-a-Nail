package com.example.nailytics

import com.nixsensor.universalsdk.IDeviceScanner
import android.content.Context
import android.util.Log
import com.nixsensor.universalsdk.DeviceScanner
import com.nixsensor.universalsdk.IDeviceCompat
import com.nixsensor.universalsdk.DeviceStatus
import android.graphics.Color
import com.nixsensor.universalsdk.CommandStatus
import com.nixsensor.universalsdk.IMeasurementData
import com.nixsensor.universalsdk.Illuminant
import com.nixsensor.universalsdk.Observer
import com.nixsensor.universalsdk.OnDeviceResultListener
import com.nixsensor.universalsdk.ScanMode
import kotlin.math.pow
import kotlin.math.sqrt

/*
Handles the Nix scanning, connecting, and measuring.
*/

object NixSensorManager {

/***************************************************************************************************
*                             SECTION THAT CONNECTS TO NIX SENSOR                                  *
****************************************************************************************************/
    // Tag used for Logcat messages.
    private const val TAG = "NixSensorManager"

    // Stores the active Nix Bluetooth scanner object. Keep this so we can later stop scanning if needed!
    private var scanner: DeviceScanner? = null

    // Stores the currently connected Nix device.
    // Main5 can use this later to measure/analyze color.
    var connectedDevice: IDeviceCompat? = null
        private set

    /* Stores all discovered Nix devices during the current scan session.
    val discoveredDevices = mutableListOf<IDeviceCompat>()
    */

    // Tracks whether we already started connecting. This prevents connecting to multiple
    // devices if scan finds several nearby sensors.
    private var isConnecting = false

    // Checks whether the app already has the Bluetooth permissions required by the Nix SDK.
    fun hasBluetoothPermissions(context: Context): Boolean {
        // Uses the Nix SDK helper function to verify permissions.
        return IDeviceScanner.isBluetoothPermissionGranted(context)
    }

    // Returns the exact Bluetooth permissions required by the Nix SDK.
    // The SDK automatically handles Android-version differences internally.
    fun requiredBluetoothPermissions(): Array<String> {
        return IDeviceScanner.requiredBluetoothPermissions
    }

    // Starts scanning and automatically connects to the first Nix device found.
    fun startScan(
        context: Context,  // Current screen/activity context.
        onScanStarted: () -> Unit,
        onDeviceFound: (IDeviceCompat) -> Unit, // Callback that runs whenever a new device is discovered.
        onConnected: (IDeviceCompat) -> Unit,
        onDisconnected: (IDeviceCompat, DeviceStatus) -> Unit,
        onError: (String) -> Unit
    ) {
        // Reset connection flag before starting a new search.
        isConnecting = false

        // Clear old connected device before starting a new scan.
        connectedDevice = null

        // Creates listens for scanner start/stop state changes.
        val scannerStateListener = object : IDeviceScanner.OnScannerStateChangeListener {
            // Runs when Bluetooth scanning begins.
            override fun onScannerStarted(sender: IDeviceScanner) {
                Log.d(TAG, "Scanner started")

                // Runs the optional callback.
                onScanStarted()
            }

            // Runs when Bluetooth scanning stops.
            override fun onScannerStopped(sender: IDeviceScanner) {
                Log.d(TAG, "Scanner stopped")
            }
        }

        // Creates listener for discovered Nix devices.
        val deviceFoundListener = object : IDeviceScanner.OnDeviceFoundListener {

            // Runs whenever the scanner discovers a nearby Nix device.
            override fun onScanResult(
                sender: IDeviceScanner,
                device: IDeviceCompat
            ) {
                // If we already started connecting, ignore additional devices.
                if (isConnecting) return

                // Mark that we are now trying to connect to a device.
                isConnecting = true

                // Tell Main2 a device was found.
                onDeviceFound(device)

                // Stop scanning because we only want the first nearby device.
                stopScan()

                // Connect to the discovered device.
                connectToDevice(
                    device = device,
                    onConnected = onConnected,
                    onDisconnected = onDisconnected,
                    onError = onError
                )
            }
        }

        try {
            // Create the Nix scanner using the app context.
            scanner = DeviceScanner(context.applicationContext)

            // Attach scanner state listener.
            scanner?.setOnScannerStateChangeListener(scannerStateListener)

            // Start scanning for nearby Nix devices.
            scanner?.start(listener = deviceFoundListener)

        } catch (e: Exception) {
            // Reset connection flag if scanning fails.
            isConnecting = false

            // Send readable error message back to Main2.
            onError("Could not start Nix scan: ${e.message}")
        }
    }

    // Connects to a discovered Nix device.
    private fun connectToDevice(
        device: IDeviceCompat,
        onConnected: (IDeviceCompat) -> Unit,
        onDisconnected: (IDeviceCompat, DeviceStatus) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            // Calls the Nix SDK connect function.
            device.connect(object : IDeviceCompat.OnDeviceStateChangeListener {

                // Runs when the Nix device successfully connects.
                override fun onConnected(sender: IDeviceCompat) {
                    Log.d(TAG, "Connected to ${sender.name}")

                    // Save the connected device globally so Main5 can use it.
                    connectedDevice = sender

                    // Reset connecting flag.
                    isConnecting = false

                    // Tell Main2 connection succeeded.
                    onConnected(sender)
                }

                // Runs if the device disconnects.
                override fun onDisconnected(
                    sender: IDeviceCompat,
                    status: DeviceStatus
                ) {
                    Log.d(TAG, "Disconnected from ${sender.name}: $status")

                    // Clear saved device if it disconnects.
                    if (connectedDevice?.id == sender.id) {
                        connectedDevice = null
                    }

                    // Reset connecting flag.
                    isConnecting = false

                    // Tell Main2/Main5 the device disconnected.
                    onDisconnected(sender, status)
                }

                // Runs when battery state changes.
                override fun onBatteryStateChanged(
                    sender: IDeviceCompat,
                    newState: Int
                ) {
                    Log.d(TAG, "Battery state changed: $newState")
                }

                // Runs when external power state changes.
                override fun onExtPowerStateChanged(
                    sender: IDeviceCompat,
                    newState: Boolean
                ) {
                    Log.d(TAG, "External power state changed: $newState")
                }
            })

        } catch (e: Exception) {
            // Reset connecting flag if connection fails.
            isConnecting = false

            // Send readable error message back to Main2.
            onError("Could not connect to ${device.name}: ${e.message}")
        }
    }

    // Stops scanning for nearby Nix devices.
    fun stopScan() {
        scanner?.stop()
        scanner = null
    }

    // Disconnects from the current Nix device.
    fun disconnect() {
        connectedDevice?.disconnect()
        connectedDevice = null
        isConnecting = false
    }


/***************************************************************************************************
*                     SECTION THAT MEASURES & MAPS COLOR TO ANALYTE VALUE                          *
****************************************************************************************************/
// Measures color using the connected Nix device, then
// compares the measured RGB color to the current analyte chart.
    fun measureAndMatchCurrentAnalyte(
        onSuccess: (NixColorMatchResult) -> Unit,
        onError: (String) -> Unit
    ) {
        // Get the device that Main2 connected to earlier.
        val device = connectedDevice

        // If no device is connected, we cannot measure.
        if (device == null) {
            onError("No Nix device connected.")
            return
        }

        // Start Nix measurement.
        device.measure(object : OnDeviceResultListener {

            // This runs when the Nix SDK returns measurement data.
            override fun onDeviceResult(
                status: CommandStatus,
                measurements: Map<ScanMode, IMeasurementData>?
            ) {
                // If the SDK says measurement failed, stop and return error.
                if (status != CommandStatus.SUCCESS || measurements == null) {
                    onError("Nix measurement failed: $status")
                    return
                }


                // Extract measured RGB array from Nix measurement.
                val measuredRgbArray = extractRgbColorFromMeasurements(measurements)

                // If no RGB color was found, stop and return error.
                if (measuredRgbArray == null) {
                    onError("Could not read RGB color from Nix measurement.")
                    return
                }

                // Convert RGB array into Android color Int.
                val measuredColor = rgbArrayToColorInt(measuredRgbArray)

                // Find the closest color value in the currently selected analyte chart.
                val closestMatch = findClosestColorMatch(measuredColor)

                // Return the final matched result back to Main5.
                onSuccess(
                    NixColorMatchResult(
                        measuredColor = measuredColor,
                        closestLabel = closestMatch.closestValue.label,
                        closestColor = closestMatch.closestValue.color
                    )
                )
            }
        })
    }
    
//HELPER FUNCTIONS TO measureAndMatchCurrentAnalyte()
//1. Converts RGB IntArray into Android Color Int.
    private fun rgbArrayToColorInt(rgb: IntArray): Int {

        // Safety check in case RGB array is malformed.
        if (rgb.size < 3) {
            return Color.BLACK
        }

        // Convert:
        // [r, g, b]
        // into Android packed color Int.
        return Color.rgb(
            rgb[0],
            rgb[1],
            rgb[2]
        )
    }

/* 2. Extracts one RGB color from the Nix measurement map.
       -> Nix docs state that all Nix devices provide color data for D50/2° reference white,
       while D65 availability depends on the device type. D50/2° is the safest cross-device choice.

   Observer.CIE1931 = 2 degrees = standard human vision model
   Illuminant = assumed standard lighting condition used to compute color values
   Reference white point = the white baseline paired with that illuminant/observer
   */
    private fun extractRgbColorFromMeasurements(
        measurements: Map<ScanMode, IMeasurementData>
    ): IntArray? {
        // Go through each measurement returned by the Nix sensor.
        for (measurement in measurements.values) {
            // Check whether this measurement contains color data.
            // D65 + 2 degree observer are standard/default color science settings.
            if ( measurement.providesColor(illuminant = Illuminant.D50, observer = Observer.CIE1931) ) {
                // Convert measurement into Nix color data.
                val colorData = measurement.toColorData(illuminant = Illuminant.D50, observer = Observer.CIE1931)

                // Return measured RGB color array.
                // Example:
                // [155, 82, 41]
                return colorData?.rgbValue
            }
        }

        // No usable color measurement was found.
        return null
    }

//3. Finds the closest color in the currently selected analyte chart.
    private fun findClosestColorMatch(
        measuredColor: Int
    ): ColorMatchResult {
        // Gets the color chart for the currently selected analyte.
        val currentChart = AnalyteColorChartManager.getChart( AnalyteChartUIHelper.selectedAnalyteId )

        // Stores the best match found so far.
        var bestMatch: ColorMatchResult? = null

        // Loops through every value/color in the selected analyte chart.
        for (chartValue in currentChart) {

            // Calculates RGB Euclidean distance.
            val rgbDistance = calculateRgbDistance(measuredColor, chartValue.color)

            // Calculates LAB color distance.
            val labDistance = calculateLabDistance(measuredColor, chartValue.color)

            // Normalizes RGB distance to approximately 0.0–1.0.
            // Max RGB distance is sqrt(255^2 + 255^2 + 255^2) ≈ 441.67.
            val normalizedRgbDistance: Double = rgbDistance / 441.67

            // Normalizes LAB distance roughly to 0.0–1.0.
            // 100 is a practical scale factor for Delta E style distances.
            val normalizedLabDistance: Double = labDistance / 100.0

            // Combines both distances into one score.
            // Lower score = closer match.
            val combinedScore = (0.4 * normalizedRgbDistance) + (0.6 * normalizedLabDistance)

            // Prints RGB distance for debugging.
            Log.d( TAG, "RGB distance to ${chartValue.label}: $rgbDistance" )

            // Prints LAB distance for debugging.
            Log.d( TAG, "LAB distance to ${chartValue.label}: $labDistance" )

            // Prints combined score for debugging.
            Log.d( TAG, "Combined score to ${chartValue.label}: $combinedScore" )

            // Creates a result object for this chart value.
            val currentResult = ColorMatchResult(
                closestValue = chartValue,
                rgbDistance = rgbDistance,
                labDistance = labDistance,
                combinedScore = combinedScore
            )

            // If this is the first result OR this result is better, save it.
            if (bestMatch == null || currentResult.combinedScore < bestMatch!!.combinedScore) {
                bestMatch = currentResult
            }
        }

        // Returns the best match.
        return bestMatch!!
    }

    // Calculates RGB distance between two colors.
// Smaller value means the colors are more similar.
    private fun calculateRgbDistance(
        colorA: Int,
        colorB: Int
    ): Double {
        // Extract RGB channels from first color.
        val rA = Color.red(colorA)
        val gA = Color.green(colorA)
        val bA = Color.blue(colorA)

        // Extract RGB channels from second color.
        val rB = Color.red(colorB)
        val gB = Color.green(colorB)
        val bB = Color.blue(colorB)

        // Calculate channel differences.
        val rDiff = (rA - rB).toDouble()
        val gDiff = (gA - gB).toDouble()
        val bDiff = (bA - bB).toDouble()

        // Return Euclidean RGB distance.
        return sqrt(rDiff.pow(2.0) + gDiff.pow(2.0) + bDiff.pow(2.0))
    }

    // Calculates LAB distance between two Android RGB color Ints.
    private fun calculateLabDistance(
        colorA: Int,
        colorB: Int
    ): Double {

        // Convert first RGB color to LAB.
        val labA = rgbToLab(colorA)

        // Convert second RGB color to LAB.
        val labB = rgbToLab(colorB)

        // Calculate L channel difference.
        val lDiff = labA[0] - labB[0]

        // Calculate a channel difference.
        val aDiff = labA[1] - labB[1]

        // Calculate b channel difference.
        val bDiff = labA[2] - labB[2]

        // Return simple Delta E 1976 distance.
        return sqrt(
            lDiff.pow(2.0) +
                    aDiff.pow(2.0) +
                    bDiff.pow(2.0)
        )
    }

    // Converts Android RGB color Int to LAB using AndroidX ColorUtils.
    private fun rgbToLab(
        color: Int
    ): DoubleArray {

        // Creates output array for L*, a*, b* values.
        val lab = DoubleArray(3)

        // Converts RGB channels into LAB.
        androidx.core.graphics.ColorUtils.RGBToLAB(
            Color.red(color),
            Color.green(color),
            Color.blue(color),
            lab
        )

        // Returns LAB array:
        // lab[0] = L*
        // lab[1] = a*
        // lab[2] = b*
        return lab
    }

}

/*
    ColorMatchResult = internal matching/debug/scoring object
    NixColorMatchResult = final clean result returned to Main5

    Later might want to improve color to analyte mapping accuracy by adding more data:
        - confidence score
        - spectral similarity score
        - top 3 matches
        - calibration quality
        - wavelength fit metrics

    --> ColorMatchResult is the perfect place for those!!

    Meanwhile Main5 only needs:
        - "What was measured?"
        - "What was the closest analyte value?"
        - "What color should I display?"

    So keeping both ColorMatchResult & NixColorMatchResult classes is cleaner and more scalable.
*/

// Final measurement result returned to Main5.
// -> This lets me return more than just the label.
data class NixColorMatchResult(
    val measuredColor: Int,  // Measured RGB color from the Nix sensor.

    // Closest analyte label.
    // Example: "pH 5", "40 mM", etc.
    val closestLabel: String,

    // Closest analyte chart color.
    val closestColor: Int
)

// // Internal helper object used while comparing chart colors.
private data class ColorMatchResult(
    val closestValue: ColorChartValue,  // The analyte color chart value that matched best.
    val rgbDistance: Double,  // RGB Euclidean distance between measured color and chart color.
    val labDistance: Double,  // LAB/Delta-E-style distance between measured color and chart color.
    val combinedScore: Double  // Combined normalized score used to choose the closest match.
)