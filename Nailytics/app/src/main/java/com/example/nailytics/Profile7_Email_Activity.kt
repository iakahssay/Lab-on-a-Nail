package com.example.nailytics

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Profile7_Email_Activity : AppCompatActivity()   {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start with the first screen
        setContentView(R.layout.profile_email)

        //This screen is a bit unique as it doesn't have a 'next' screen. Instead, its more like a pop up screen, where it
        // saves the user's name change, then goes back to original profile screen
        moveToProfileMainScreen() //Listener(s) that will move this screen back to whichever screen called on it first


        //TODO: ADD TEXT INPUTS

        //Listeners for the (navigation) tab bar
        NavBar_Helper.moveToHome(this)
        NavBar_Helper.moveToSummary(this)
        NavBar_Helper.moveToProfile(this)
    }

    //1) PAGE FLOW FUNCTION
    private fun moveToProfileMainScreen() {
        //Sets the shared onClickListener for multiple buttons
        val goBack  = View.OnClickListener{
            finish()
        }

        //Implements that listener for the specific buttons we want
        findViewById<View>(R.id.back_button).setOnClickListener(goBack)
        findViewById<View>(R.id.save_button).setOnClickListener(goBack)
    }
}