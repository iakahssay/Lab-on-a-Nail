package com.example.nailytics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast

class Main3_Changing_Colors_Activity : AppCompatActivity()  {
    private var lastEditedColorInput: String = "RGB"
    private var isSyncingColorInputs = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start with your first exported screen
        setContentView(R.layout.main3_changing_colors)

        //Shows the selected/update analyte color chart
        AnalyteChartUIHelper.updateColorChart(this)

        //Show the analyte TYPE dropdown menu and the dimming overlay feature that comes with it
        findViewById<View>(R.id.dropDownSection).setOnClickListener {
            AnalyteChartUIHelper.showAnalyteDropdown(this, it)
        }

        //Show the analyte VALUE dropdown and the dimming overlay feature that comes with it
        findViewById<View>(R.id.analyte_value_dropDown).setOnClickListener {
            AnalyteChartUIHelper.showAnalyteValueDropdown(this, it)
        }

        //Syncs RGB & HEX values automatically as user is editing the text
        // When user edits RGB -> Show the HEX equivalent
        // When user edits HEX -> Show the RGB equivalent
        setupColorInputSync()

        //This screen is a bit unique as it doesn't have a 'next' screen. Instead, its more like a pop up screen, where it
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

        findViewById<View>(R.id.save_button).setOnClickListener {
            saveUserColorInput() //Saves/updates the changed color in the analyteColorChart map, then updates the color chart card on screen
            goBack.onClick(it) //Then goes back to the previous screen
        }
    }

    //2) SELECTING WHICH ANALYTE VALUE'S COLOR USER WANTS TO CHANGE
    private fun saveUserColorInput() {
        // Grab the HEX and RGB input fields from main3_changing_colors.xml
        val rInput = findViewById<EditText>(R.id.r_input)
        val gInput = findViewById<EditText>(R.id.g_input)
        val bInput = findViewById<EditText>(R.id.b_input)
        val hexInput = findViewById<EditText>(R.id.hex_color_input)

        val newColor: Int

        //Creates a new Color only when RGB values are edited
        if (lastEditedColorInput == "HEX") {
            // Convert text input into String value
            val hex = hexInput.text.toString().trim()

            // Validate HEX value
            if (!hex.matches(Regex("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{8})$"))) {
                Toast.makeText(this, "Please enter a valid HEX color.", Toast.LENGTH_SHORT).show()
                return
            }

            // Create the new color from the user's input
            newColor = Color.parseColor(hex)

        } else { //Creates a new Color only when HEX value is edited
            // Convert text input into Int values
            val r = rInput.text.toString().toIntOrNull()
            val g = gInput.text.toString().toIntOrNull()
            val b = bInput.text.toString().toIntOrNull()

            // Make sure the user entered valid numbers
            if (r == null || g == null || b == null) {
                Toast.makeText(this, "Please enter valid RGB values.", Toast.LENGTH_SHORT).show()
                return
            }

            // RGB values must be between 0 and 255
            if (r !in 0..255 || g !in 0..255 || b !in 0..255) {
                Toast.makeText(this, "RGB values must be between 0 and 255.", Toast.LENGTH_SHORT).show()
                return
            }

            // Create the new color from the user's input
            newColor = Color.rgb(r, g, b)
        }

        // Save color into the selected analyte value
        AnalyteColorChartManager.updateColor(
            AnalyteChartUIHelper.selectedAnalyteId,
            AnalyteChartUIHelper.selectedAnalyteValueName,
            newColor
        )

        // Update the visible color chart so the change shows immediately
        AnalyteChartUIHelper.updateColorChart(this)
    }

    //3) Nice UX Feature -> Syncing RGB & HEX automatically:
    // When user edits RGB -> Show the HEX equivalent
    // When user edits HEX -> Show the RGB equivalent
    private fun setupColorInputSync() {
        // Grab the HEX and RGB input fields from main3_changing_colors.xml
        val rInput = findViewById<EditText>(R.id.r_input)
        val gInput = findViewById<EditText>(R.id.g_input)
        val bInput = findViewById<EditText>(R.id.b_input)
        val hexInput = findViewById<EditText>(R.id.hex_color_input)

        val rgbWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (isSyncingColorInputs) return

                lastEditedColorInput = "RGB"

                // Convert text input into Int values
                val r = rInput.text.toString().toIntOrNull()
                val g = gInput.text.toString().toIntOrNull()
                val b = bInput.text.toString().toIntOrNull()

                // Make sure the user entered valid numbers
                if (r in 0..255 && g in 0..255 && b in 0..255) {
                    isSyncingColorInputs = true //Start converting RBG to HEX

                    // When user edits RGB -> Show the HEX equivalent
                    val hex = String.format("#%02X%02X%02X", r, g, b) //Convert RBG to HEX
                    hexInput.setText(hex) //Show synced HEX value

                    isSyncingColorInputs = false //No longer converting RBG to HEX
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        rInput.addTextChangedListener(rgbWatcher)
        gInput.addTextChangedListener(rgbWatcher)
        bInput.addTextChangedListener(rgbWatcher)

        hexInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (isSyncingColorInputs) return

                lastEditedColorInput = "HEX"

                // Convert text input into String value
                val hex = hexInput.text.toString().trim()

                // Make sure the user entered valid numbers
                if (hex.matches(Regex("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{8})$"))) {
                    isSyncingColorInputs = true //Start converting HEX to RGB

                    // When user edits HEX -> Show the RGB equivalent
                    val color = Color.parseColor(hex)  //Convert RBG to HEX
                    rInput.setText(Color.red(color).toString()) //Show synced R value
                    gInput.setText(Color.green(color).toString()) //Show synced G value
                    bInput.setText(Color.blue(color).toString()) //Show synced B value

                    isSyncingColorInputs = false //No longer converting HEX to RGB
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

}