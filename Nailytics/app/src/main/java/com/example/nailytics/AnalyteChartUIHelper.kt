package com.example.nailytics

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import android.widget.TextView

object AnalyteChartUIHelper {
//For the analyte TYPE dropdown menu (in pages main2-mainRESULTS)
    // Tracks which analyte TYPE is currently selected.
    // Example: pH levels, Glucose levels, Nitrate levels, etc
    var selectedAnalyteId: Int = R.id.item_saliva_ph
    var selectedAnalyteName: String = "pH levels (saliva)"

//For the analyte VALUE dropdown menu (in main3 screen)
    // Tracks which analyte VALUE is currently selected.
    // Ex: pH 5, ph 6, ph 7, etc.
    var selectedAnalyteValueIndex: Int = 0
    var selectedAnalyteValueName: String = AnalyteColorChartManager.getChart(selectedAnalyteId)[0].label


//1) COLOR CHART UI FUNCTION
    //Updates the analyte color chart shown on the main screen
    //-> Is used/called on in selectAnalyte()
    fun updateColorChart(activity:Activity) {
        val values = AnalyteColorChartManager.getChart(selectedAnalyteId)  //returns the list of items in the analyteMap[analyteID]

        val colorViews: List<Int?> = listOf(
                R.id.item0_color,
                R.id.item1_color,
                R.id.item2_color,
                R.id.item3_color
        )

        val labelViews: List<Int?> = listOf(
                R.id.item0_label,
                R.id.item1_label,
                R.id.item2_label,
                R.id.item3_label
        )

        for (i in values.indices) {
            colorViews[i]?.let { activity.findViewById<View?> (it)?.setBackgroundColor(values[i].color) }
            labelViews[i]?.let { activity.findViewById<TextView?> (it)?.text = values[i].label}
        }

        //Initializes the analyte selector text on the main screen (on the top of the chart)
        activity.findViewById<TextView?>(R.id.analyte_type).text = selectedAnalyteName

        //Initializes/updates selected analyte_value if screen has analyte_value (which will only be true in Main3 Activity).
        // Does nothing on screens that don't.
        resetSelectedAnalyteValue(activity)
    }

//2) ANALYTE TYPE DROPDOWN FUNCTION
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
            overlay?.visibility = View.GONE
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
        popupWindow: PopupWindow,
    ) {
        selectedAnalyteId = itemId
        selectedAnalyteName = labelText
        activity.findViewById<TextView>(R.id.analyte_type).text = selectedAnalyteName // Updates the analyte selector text on the main screen.
        updateColorChart(activity) //Updates the color charts/boxes shown on the main screen
        updateCheckmarks(popupView) // updates the checkmark to show up only next to the analyte selected
        popupWindow.dismiss() // Closes the dropdown menu after am analyte has been selected
    }

    // Converts dp values into pixels because PopupWindow expects pixel values.
    fun Int.dpToPx(activity: Activity): Int {
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


//3) ANALYTE VALUE DROPDOWN OPTION
    fun showAnalyteValueDropdown(
        activity:Activity, //the current class activity (ex: Main1_Activity's 'this')
        anchor: View,
    ){

        val overlay = activity.findViewById<View?>(R.id.screen_dim_overlay) // Finds the dark overlay view from the current screen layout.
        overlay?.visibility = View.VISIBLE // Makes the overlay visible so the screen looks dimmed behind the dropdown.

        // Converts the dropdown XML layout into an actual View object.
        val popupView = LayoutInflater.from(activity)?.inflate(R.layout.analyte_value_dropdown_menu, null)

        // Creates a popup window using the dropdown layout.
        val popupWindow = PopupWindow(
            popupView,          // The dropdown UI.
            250.dpToPx(activity),       // Popup width.
            204.dpToPx(activity),       // Popup height.
            true                // Allows the popup to receive focus.
        )

        // Shows the dropdown directly below the tapped button/view.
        popupWindow.showAsDropDown(anchor, 0, 8.dpToPx(activity))

        // Get the current analyte chart.
        // This automatically changes depending on selectedAnalyteId.
        val chart = AnalyteColorChartManager.getChart(selectedAnalyteId)

       // Update the analyte value dropdown items (based on whichever analyte type was picked)
        val itemViews = listOf(
            popupView?.findViewById<View>(R.id.item0),
            popupView?.findViewById<View>(R.id.item1),
            popupView?.findViewById<View>(R.id.item2),
            popupView?.findViewById<View>(R.id.item3)
        )

        val labelViews = listOf(
            popupView?.findViewById<TextView>(R.id.item0_label),
            popupView?.findViewById<TextView>(R.id.item1_label),
            popupView?.findViewById<TextView>(R.id.item2_label),
            popupView?.findViewById<TextView>(R.id.item3_label)
        )

        // Fill dropdown rows using the current analyte's chart labels.
        // Example: pH chart shows pH 5–pH 8.
        // Glucose chart shows 40 mM–160 mM.
        for (i in chart.indices) {
            labelViews[i]?.text = chart[i].label

            itemViews[i]?.setOnClickListener {
                selectedValue(
                    activity = activity,
                    index = i,
                    labelText = chart[i].label,
                    popupWindow = popupWindow
                )
            }
        }

        /*
        Similar to this, but for every R.id.item#:
            // Handles the first dropdown option.
            popupView?.findViewById<View?>(R.id.item0)?.setOnClickListener {
                selectValue(
                    activity,
                    0,
                    chart[0].label,
                    popupWindow
                )
            }
        */

        // Allows the user to tap outside the popup to close it.
        popupWindow.isOutsideTouchable = true
        // Gives the popup a transparent background so outside-tap dismissal works correctly.
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Runs this code whenever the popup closes.
        popupWindow.setOnDismissListener {
            // Hides the dark overlay after the dropdown is dismissed.
            overlay?.visibility = View.GONE
        }
    }

    private fun selectedValue(
        activity: Activity,
        index: Int,
        labelText: String,
        popupWindow: PopupWindow
    ) {
        // Save which chart item the user is editing.
        selectedAnalyteValueIndex = index
        selectedAnalyteValueName = labelText

        // Update the selected value text on Main3.
        activity.findViewById<TextView>(R.id.analyte_value).text = selectedAnalyteValueName
        // Closes the dropdown menu after am analyte has been selected
        popupWindow.dismiss()
    }

    // Whenever the analyte TYPE changes, this function resets the selected VALUE to the first item.
    // Example: if user switches from pH saliva to glucose saliva,
    // the selected value should change from "pH 5" to "40 mM".
    fun resetSelectedAnalyteValue(activity: Activity) {
        selectedAnalyteValueIndex = 0

        val chart = AnalyteColorChartManager.getChart(selectedAnalyteId)
        selectedAnalyteValueName = chart[0].label

        // Safe: only Main3 has this TextView.
        // Other screens return null, so no crash.
        activity.findViewById<TextView?>(R.id.analyte_value)?.text = selectedAnalyteValueName
    }


}

