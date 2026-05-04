package com.example.nailytics

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity

class Profile1_Main_Activity  : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start with the first screen
        setContentView(R.layout.profile_main)

        //Show the logout popup message and the dimming overlay feature that comes with it
        findViewById<View>(R.id.logout_button).setOnClickListener {
            showLogoutPopup(this, this.findViewById<View>(R.id.personal_info_section))
        }

        //Listeners that will move this screen (profile_main) to the next (profile_name, profile_bday, etc.)
        moveToProfileName()
        moveToProfileBday()
        moveToProfileGender()
        moveToProfileEmail()
        moveToProfileBMI()


        //Listeners for the (navigation) tab bar
        NavBar_Helper.moveToHome(this)
        NavBar_Helper.moveToSummary(this)
        //For the back button on the top of the screen
        NavBar_Helper.moveToPreviousScreen(this)
    }


//1) PAGE FLOW FUNCTIONS
    private fun moveToProfileName(){
        findViewById<View>(R.id.name_row).setOnClickListener {
            startActivity(Intent(this, Profile3_Name_Activity::class.java))
        }
    }

    private fun moveToProfileBday(){
        findViewById<View>(R.id.bday_row).setOnClickListener {
            startActivity(Intent(this, Profile4_Bday_Activity::class.java))
        }
    }

    private fun moveToProfileGender(){
        findViewById<View>(R.id.gender_row).setOnClickListener {
            startActivity(Intent(this, Profile5_Gender_Activity::class.java))
        }
    }

    private fun moveToProfileEmail(){
        findViewById<View>(R.id.email_row).setOnClickListener {
            startActivity(Intent(this, Profile7_Email_Activity::class.java))
        }
    }

    private fun moveToProfileBMI(){
        //Sets the shared onClickListener for multiple buttons
        val goToBMI  = View.OnClickListener{
            startActivity(Intent(this, Profile8_BMI_Activity::class.java))
        }

        //Implements that listener for the specific buttons we want
        findViewById<View>(R.id.bmi_row).setOnClickListener(goToBMI)
        findViewById<View>(R.id.weight_row).setOnClickListener(goToBMI)
        findViewById<View>(R.id.height_row).setOnClickListener(goToBMI)
    }


//2) LOGOUT POPUP FUNCTION
    // Shows the logout popup when the user taps the logout button.
    private fun showLogoutPopup(
        activity: Activity, //the current class activity (ex: Main1_Activity's 'this')
        anchor: View,
    ){
        val overlay = activity.findViewById<View?>(R.id.screen_dim_overlay) // Finds the dark overlay view from the current screen layout.
        overlay?.visibility = View.VISIBLE // Makes the overlay visible so the screen looks dimmed behind the popup message.

        // Converts the logout_message_popup XML layout into an actual View object.
        val popupView = LayoutInflater.from(activity)?.inflate(R.layout.logout_message_popup, null)

        // Creates a popup window using the logout_message_popup layout.
        val popupWindow = PopupWindow(
            popupView,          // The dropdown UI.
            330.dpToPx(activity),       // Popup width.
            122.dpToPx(activity),       // Popup height.
            true                // Allows the popup to receive focus.
        )

        // Shows the popup in the middle of the screen
        popupWindow.showAsDropDown(anchor, 30.dpToPx(activity), -80.dpToPx(activity))

        // Handles the popup's logout button option -> If user decides to logout, go to profile_login screen
        popupView?.findViewById<View?>(R.id.logout_button)?.setOnClickListener {
           startActivity(Intent(this, Profile2_Login_Activity::class.java))
        }

        // Handles the popup's cancel button option.
        popupView?.findViewById<View?>(R.id.cancel_button)?.setOnClickListener {
            popupWindow.dismiss() // Closes the dropdown menu after the cancel button has been selected
        }

        // Gives the popup a transparent background so outside-tap dismissal works correctly.
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Runs this code whenever the popup closes.
        popupWindow.setOnDismissListener {
            // Hides the dark overlay after the popup is dismissed.
            overlay.visibility = View.GONE
        }
}
    //HELPER FUNCTIONS FOR showLogoutPopup()
    // Converts dp values into pixels because PopupWindow expects pixel values.
    private fun Int.dpToPx(activity: Activity): Int {
        // Multiplies the dp number by the screen density and returns it as an integer.
        return (this * activity.resources.displayMetrics.density).toInt()
    }

}