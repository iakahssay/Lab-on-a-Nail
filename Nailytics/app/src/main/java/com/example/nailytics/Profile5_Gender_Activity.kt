package com.example.nailytics


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Profile5_Gender_Activity : AppCompatActivity()   {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start with the first screen
        setContentView(R.layout.profile_gender)

        // Define a list of all the gender-identifying option IDs
        val optionIds = listOf(
            R.id.female_option, R.id.male_option, R.id.nonbinary_option,
            R.id.transgender_female_option, R.id.transgender_male_option,
            R.id.prefer_not_to_say_option, R.id.prefer_to_self_describe_option
        )

        // Set the same listener for every individual option, and
        // update/check for which listener (gender row) gets selected
        optionIds.forEach { id ->
            findViewById<View>(id)?.setOnClickListener { clickedView ->
                updateCheckmarks(clickedView)
            }
        }

        //Listener that will move this screen to the next (profile_gender2)
        //moveToProfileGender2()

        //Listener(s) that will move this screen back to whichever screen called on it first
        moveToProfileMainScreen()

        //TODO: ADD TEXT INPUTS

        //Listeners for the (navigation) tab bar
        NavBar_Helper.moveToHome(this)
        NavBar_Helper.moveToSummary(this)
        NavBar_Helper.moveToProfile(this)
    }

//2) PAGE FLOW FUNCTION
    private fun moveToProfileMainScreen() {
        //Sets the shared onClickListener for multiple buttons
        val goBack  = View.OnClickListener{
            finish()
        }

        //Implements that listener for the specific buttons we want
        findViewById<View>(R.id.back_button).setOnClickListener(goBack)
        findViewById<View>(R.id.save_button).setOnClickListener(goBack)
    }

    private fun moveToProfileGender2(){
        findViewById<View>(R.id.prefer_to_self_describe_option).setOnClickListener {
            startActivity(Intent(this, Profile6_Gender_Self_Describe_Activity::class.java))
        }
    }

//3) GENDER SELECTION FUNCTIONS
    //Updates the checkmarks to show which one was chosen after a user clicked on the gneder
    private fun updateCheckmarks(selectedGender: View) {
        findViewById<View?>(R.id.female_option_checkmark)?.visibility = View.GONE
        findViewById<View?>(R.id.male_option_checkmark)?.visibility = View.GONE
        findViewById<View?>(R.id.nonbinary_option_checkmark)?.visibility = View.GONE
        findViewById<View?>(R.id.transgender_female_option_checkmark)?.visibility = View.GONE
        findViewById<View?>(R.id.transgender_male_option_checkmark)?.visibility = View.GONE
        findViewById<View?>(R.id.prefer_not_to_say_option_checkmark)?.visibility = View.GONE
        findViewById<View?>(R.id.prefer_to_self_describe_option_checkmark)?.visibility = View.GONE

        when (selectedGender.id){
            R.id.female_option -> findViewById<View?>(R.id.female_option_checkmark)?.visibility = View.VISIBLE

            R.id.male_option -> findViewById<View?>(R.id.male_option_checkmark)?.visibility = View.VISIBLE

            R.id.nonbinary_option -> findViewById<View?>(R.id.nonbinary_option_checkmark)?.visibility = View.VISIBLE

            R.id.transgender_female_option -> findViewById<View?>(R.id.transgender_female_option_checkmark)?.visibility = View.VISIBLE

            R.id.transgender_male_option -> findViewById<View?>(R.id.transgender_male_option_checkmark)?.visibility = View.VISIBLE

            R.id.prefer_not_to_say_option -> findViewById<View?>(R.id.prefer_not_to_say_option_checkmark)?.visibility = View.VISIBLE

            R.id.prefer_to_self_describe_option -> {
                findViewById<View>(R.id.prefer_to_self_describe_option_checkmark)?.visibility = View.VISIBLE
                startActivity(Intent(this, Profile6_Gender_Self_Describe_Activity::class.java))
            }
        }
    }
}