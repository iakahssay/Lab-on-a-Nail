package com.example.nailytics

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import android.widget.TextView

public var selectedAnalyteId: Int = R.id.item_saliva_ph
public var selectedAnalyteName: String = "pH levels (saliva)"
object AnalyteChartUIHelper {

    //1) COLOR CHART UI FUNCTION
    //Updates the analyte color chart shown on the main screen
    fun updateColorChart(activity:Activity) {
        val values = AnalyteColorChartManager.getChart(selectedAnalyteId)  //returns the list of items in the analyteMap[analyteID]

        val colorViews = listOf(
                R.id.item0_color,
                R.id.item1_color,
                R.id.item2_color,
                R.id.item3_color
        )

        val labelViews = listOf(
                R.id.item0_label,
                R.id.item1_label,
                R.id.item2_label,
                R.id.item3_label
        )

        for (i in values.indices) {
            activity.findViewById<View> (colorViews[i]).setBackgroundColor(values[i].color)
            activity.findViewById<TextView> (labelViews[i]).text = values[i].label
        }

        //Initializes the analyte selector text on the main screen (on the top of the chart)
        activity.findViewById<TextView>(R.id.analyte_type).text = selectedAnalyteName
    }

    //2) DROPDOWN FUNCTION
    // Shows the analyte dropdown menu when the user taps the analyte selector.
    fun showAnalyteDropdown(
            activity:Activity, //the current class activity (ex: Main1_Activity's 'this')
            anchor: View,
        ){

        val overlay = activity.findViewById<View?>(R.id.screen_dim_overlay) // Finds the dark overlay view from the current screen layout.
        overlay?.visibility = View.VISIBLE // Makes the overlay visible so the screen looks dimmed behind the dropdown.

        // Converts the dropdown XML layout into an actual View object.
        val popupView = LayoutInflater.from(activity)?.inflate(R.layout.analyte_dropdown_menu, null)

        // Creates a popup window using the dropdown layout.
        val popupWindow = PopupWindow(
            popupView,          // The dropdown UI.
            250.dpToPx(activity),       // Popup width.
            404.dpToPx(activity),       // Popup height.
            true                // Allows the popup to receive focus.
        )

        // Shows the dropdown directly below the tapped button/view.
        popupWindow.showAsDropDown(anchor, 0, 8.dpToPx(activity))

        // Initializes correct checkmark
        updateCheckmarks(popupView)

        // Handles the first dropdown option.
        popupView?.findViewById<View?>(R.id.item_blood_ph)?.setOnClickListener {
            selectAnalyte(
                activity,
                R.id.item_blood_ph,
                "pH levels (blood)",
                popupView,
                popupWindow
            )
        }

        // Handles the second dropdown option.
        popupView?.findViewById<View?>(R.id.item_urine_ph)?.setOnClickListener {
            selectAnalyte(
                activity,
                R.id.item_urine_ph,
                "pH levels (urine)",
                popupView,
                popupWindow
            )
        }

        // Handles the third dropdown option.
        popupView?.findViewById<View?>(R.id.item_saliva_ph)?.setOnClickListener {
            selectAnalyte(
                activity,
                R.id.item_saliva_ph,
                "pH levels (saliva)",
                popupView,
                popupWindow
            )
        }

        // Handles the fourth dropdown option.
        popupView?.findViewById<View?>(R.id.item_blood_glucose)?.setOnClickListener {
            selectAnalyte(
                activity,
                R.id.item_blood_glucose,
                "Glucose levels (blood)",
                popupView,
                popupWindow
            )
        }

        // Handles the fifth dropdown option.
        popupView?.findViewById<View?>(R.id.item_urine_glucose)?.setOnClickListener {
            selectAnalyte(
                activity,
                R.id.item_urine_glucose,
                "Glucose levels (urine)",
                popupView,
                popupWindow
            )
        }

        // Handles the sixth dropdown option.
        popupView?.findViewById<View?>(R.id.item_saliva_glucose)?.setOnClickListener {
            selectAnalyte(
                activity,
                R.id.item_saliva_glucose,
                "Glucose levels (saliva)",
                popupView,
                popupWindow
            )
        }

        // Handles the seventh dropdown option.
        popupView?.findViewById<View?>(R.id.item_blood_nitrate)?.setOnClickListener {
            selectAnalyte(
                activity,
                R.id.item_blood_nitrate,
                "Nitrate levels (blood)",
                popupView,
                popupWindow
            )
        }

        // Handles the eighth dropdown option.
        popupView?.findViewById<View?>(R.id.item_urine_nitrate)?.setOnClickListener {
            selectAnalyte(
                activity,
                R.id.item_urine_nitrate,
                "Nitrate levels (urine)",
                popupView,
                popupWindow
            )
        }

        // Handles the ninth dropdown option.
        popupView?.findViewById<View?>(R.id.item_saliva_nitrate)?.setOnClickListener {
            selectAnalyte(
                activity,
                R.id.item_saliva_nitrate,
                "Nitrate levels (saliva)",
                popupView,
                popupWindow
            )
        }

        // Allows the user to tap outside the popup to close it.
        popupWindow.isOutsideTouchable = true
        // Gives the popup a transparent background so outside-tap dismissal works correctly.
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Runs this code whenever the popup closes.
        popupWindow.setOnDismissListener {
            // Hides the dark overlay after the dropdown is dismissed.
            overlay.visibility = View.GONE
        }

    }

    //HELPER FUNCTIONS FOR showAnalyteDropdown()
    //As the user is clicking on which analyte they want to analyze from the dropdown menu, this helper function:
    // updates the analyte selector text on the main screen, updates the checkmark to show up only
    // next to the analyte selected, and updates the color charts/boxes shown on the main screen
    private fun selectAnalyte(
        activity: Activity,
        itemId: Int,
        labelText: String,
        popupView: View,
        popupWindow: PopupWindow
    ) {
        selectedAnalyteId = itemId
        selectedAnalyteName = labelText
        activity.findViewById<TextView>(R.id.analyte_type).text = selectedAnalyteName // Updates the analyte selector text on the main screen.
        updateColorChart(activity) //Updates the color charts/boxes shown on the main screen
        updateCheckmarks(popupView) // updates the checkmark to show up only next to the analyte selected
        popupWindow.dismiss() // Closes the dropdown menu after am analyte has been selected
    }

    // Converts dp values into pixels because PopupWindow expects pixel values.
    private fun Int.dpToPx(activity: Activity): Int {
        // Multiplies the dp number by the screen density and returns it as an integer.
        return (this * activity.resources.displayMetrics.density).toInt()
    }

    //Updates the checkmarks to show which one was chosen after a user clicked on the analyte
    private fun updateCheckmarks(popupView: View?) {
        popupView?.findViewById<View?>(R.id.checkmark_blood_ph)?.visibility = View.GONE
        popupView?.findViewById<View?>(R.id.checkmark_urine_ph)?.visibility = View.GONE
        popupView?.findViewById<View?>(R.id.checkmark_saliva_ph)?.visibility = View.GONE
        popupView?.findViewById<View?>(R.id.checkmark_blood_glucose)?.visibility = View.GONE
        popupView?.findViewById<View?>(R.id.checkmark_urine_glucose)?.visibility = View.GONE
        popupView?.findViewById<View?>(R.id.checkmark_saliva_glucose)?.visibility = View.GONE
        popupView?.findViewById<View?>(R.id.checkmark_blood_nitrate)?.visibility = View.GONE
        popupView?.findViewById<View?>(R.id.checkmark_urine_nitrate)?.visibility = View.GONE
        popupView?.findViewById<View?>(R.id.checkmark_saliva_nitrate)?.visibility = View.GONE

        when (selectedAnalyteId) {
            R.id.item_blood_ph -> popupView?.findViewById<View?>(R.id.checkmark_blood_ph)?.visibility = View.VISIBLE

            R.id.item_urine_ph -> popupView?.findViewById<View?>(R.id.checkmark_urine_ph)?.visibility = View.VISIBLE

            R.id.item_saliva_ph -> popupView?.findViewById<View?>(R.id.checkmark_saliva_ph)?.visibility = View.VISIBLE

            R.id.item_blood_glucose -> popupView?.findViewById<View?>(R.id.checkmark_blood_glucose)?.visibility = View.VISIBLE

            R.id.item_urine_glucose -> popupView?.findViewById<View?>(R.id.checkmark_urine_glucose)?.visibility = View.VISIBLE

            R.id.item_saliva_glucose -> popupView?.findViewById<View?>(R.id.checkmark_saliva_glucose)?.visibility = View.VISIBLE

            R.id.item_blood_nitrate -> popupView?.findViewById<View?>(R.id.checkmark_blood_nitrate)?.visibility = View.VISIBLE

            R.id.item_urine_nitrate -> popupView?.findViewById<View?>(R.id.checkmark_urine_nitrate)?.visibility = View.VISIBLE

            R.id.item_saliva_nitrate -> popupView?.findViewById<View?>(R.id.checkmark_saliva_nitrate)?.visibility = View.VISIBLE
        }
    }

}

