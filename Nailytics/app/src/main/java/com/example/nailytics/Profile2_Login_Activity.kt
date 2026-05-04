package com.example.nailytics

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Profile2_Login_Activity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start with the first screen
        setContentView(R.layout.profile_login)

        //TODO: ADD TEXT INPUTS

        //Listeners that will move this screen (profile_login) to the next (main1)
        moveToMain1()
    }

    //1) PAGE FLOW FUNCTIONS
    private fun moveToMain1() {
        findViewById<View>(R.id.continue_button).setOnClickListener {
            startActivity(Intent(this, Main1_Activity::class.java))
        }
    }

}