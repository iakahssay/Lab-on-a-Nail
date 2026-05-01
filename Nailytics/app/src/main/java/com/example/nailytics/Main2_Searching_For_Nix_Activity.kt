package com.example.nailytics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import android.widget.TextView

private var selectedAnalyteId: Int = R.id.item_3

class Main2_Searching_For_Nix_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start with your first exported screen
        setContentView(R.layout.main2_searching_for_nix)

        findViewById<View>(R.id.dropDownSection).setOnClickListener {
            showAnalyteDropdown(it)
        }

        findViewById<View>(R.id.screen_dim_overlay).setOnClickListener {
            it.visibility = View.GONE
        }

    }


//Helper Function #1
// Shows the analyte dropdown menu when the user taps the analyte selector.
    private fun showAnalyteDropdown(anchor: View) {

        val overlay = findViewById<View>(R.id.screen_dim_overlay) // Finds the dark overlay view from the current screen layout.
        overlay.visibility = View.VISIBLE // Makes the overlay visible so the screen looks dimmed behind the dropdown.

        // Converts the dropdown XML layout into an actual View object.
        val popupView = LayoutInflater.from(this).inflate(R.layout.analyte_dropdown_menu, null)

        // Creates a popup window using the dropdown layout.
        val popupWindow = PopupWindow(
            popupView,          // The dropdown UI.
            250.dpToPx(),       // Popup width.
            404.dpToPx(),       // Popup height.
            true                // Allows the popup to receive focus.
        )

    // Allows the user to tap outside the popup to close it.
        popupWindow.isOutsideTouchable = true
        // Gives the popup a transparent background so outside-tap dismissal works correctly.
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Runs this code whenever the popup closes.
        popupWindow.setOnDismissListener {
            // Hides the dark overlay after the dropdown is dismissed.
            overlay.visibility = View.GONE
        }

        // Initialize correct checkmark
        updateCheckmarks(popupView)

        popupWindow.showAsDropDown(anchor, 0, 8.dpToPx())

        // Handles the first dropdown option.
        popupView.findViewById<View>(R.id.item_blood_ph).setOnClickListener {
            selectAnalyte(
                R.id.item_blood_ph,
                "pH levels (blood)",
                popupView,
                popupWindow
            )
        }
        // Handles the second dropdown option.
        popupView.findViewById<View>(R.id.item_urine_ph).setOnClickListener {
            selectAnalyte(
                R.id.item_urine_ph,
                "pH levels (urine)",
                popupView,
                popupWindow
            )
        }

        // Handles the third dropdown option.
        popupView.findViewById<View>(R.id.item_saliva_ph).setOnClickListener {
            selectAnalyte(
                R.id.item_saliva_ph,
                "pH levels (saliva)",
                popupView,
                popupWindow
            )
        }

        // Shows the dropdown directly below the tapped button/view.
        popupWindow.showAsDropDown(anchor, 0, 8.dpToPx())

    }

//HELPER FUNCTIONS FOR showAnalyteDropdown()
    // Converts dp values into pixels because PopupWindow expects pixel values.
    private fun Int.dpToPx(): Int {
        // Multiplies the dp number by the screen density and returns it as an integer.
        return (this * resources.displayMetrics.density).toInt()
    }

    private fun updateCheckmarks(popupView: View) {
        popupView.findViewById<View>(R.id.checkmark_blood_ph).visibility = View.GONE
        popupView.findViewById<View>(R.id.checkmark_urine_ph).visibility = View.GONE
        popupView.findViewById<View>(R.id.checkmark_saliva_ph).visibility = View.GONE

        when (selectedAnalyteId) {
            R.id.item_blood_ph -> popupView.findViewById<View>(R.id.checkmark_blood_ph).visibility = View.VISIBLE

            R.id.item_urine_ph -> popupView.findViewById<View>(R.id.checkmark_urine_ph).visibility = View.VISIBLE

            R.id.item_saliva_ph -> popupView.findViewById<View>(R.id.checkmark_saliva_ph).visibility = View.VISIBLE
        }
    }

    private fun selectAnalyte(
        itemId: Int,
        labelText: String,
        popupView: View,
        popupWindow: PopupWindow
    ) {
        selectedAnalyteId = itemId

        // Updates the analyte selector text on the main screen.
        findViewById<TextView>(R.id.analyte_type).text = labelText
        updateCheckmarks(popupView)
        popupWindow.dismiss() // Closes the dropdown.
    }

}