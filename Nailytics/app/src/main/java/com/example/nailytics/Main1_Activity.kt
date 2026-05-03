package com.example.nailytics

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Main1_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start with the first screen
        setContentView(R.layout.main1)

        //Listener that will move this screen (main1) to the next (main2)
        moveToMain2()

        //Listeners for the (navigation) tab bar
        NavBar_Helper.moveToProfile(this)
        NavBar_Helper.moveToSummary(this)
    }


//PAGE FLOW FUNCTIONS
    private fun moveToMain2() {
        //Sets the shared onClickListener for multiple buttons
        val goToMain2 = View.OnClickListener {
            val intent = Intent(this, Main2_Searching_For_Nix_Activity::class.java)
            startActivity(intent)
        }

        //Implements that listener for the specific buttons we want
        findViewById<View>(R.id.start_button).setOnClickListener(goToMain2)
        findViewById<View>(R.id.color_wheel_container).setOnClickListener(goToMain2)
    }

}

/*
 If one buttons goes to a different screen:
    findViewById<View>(R.id.profile_tab).setOnClickListener {
        startActivity(Intent(this, ProfileMainActivity::class.java))
    }
 */

/*
If multiple buttons go to the same screen:

    //Sets the shared onClickListener for multiple buttons
    val goToMain2 = View.OnClickListener {
        val intent = Intent(this, Main2_Searching_For_Nix_Activity::class.java)
        startActivity(intent)
    }

    //Implements that listener for the specific buttons we want
    findViewById<View>(R.id.start_button).setOnClickListener(goToMain2)
    findViewById<View>(R.id.color_wheel_container).setOnClickListener(goToMain2)

*/