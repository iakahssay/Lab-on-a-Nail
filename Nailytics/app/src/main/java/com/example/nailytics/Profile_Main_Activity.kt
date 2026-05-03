package com.example.nailytics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Profile_Main_Activity  : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start with the first screen
        setContentView(R.layout.profile_main)

        //Listeners that will move this screen (main1) to the next (main2)
        //moveToScreen2()

        //Listeners for the (navigation) tab bar
        NavBar_Helper.moveToHome(this)
        NavBar_Helper.moveToProfile(this)
        NavBar_Helper.moveToSummary(this)
        //For the back button on the top of the screen
        NavBar_Helper.moveToPreviousScreen(this)
    }


//PAGE FLOW FUNCTIONS


}