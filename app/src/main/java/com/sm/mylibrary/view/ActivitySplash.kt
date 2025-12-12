package com.sm.mylibrary.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.sm.mylibrary.R
import com.sm.mylibrary.utils.Constants
import com.sm.mylibrary.utils.Constants.Companion.SPLASH_SCREEN_TIME_OUT
import com.sm.mylibrary.utils.SheardPreferenceViewModel


class ActivitySplash : AppCompatActivity() {

    val sheardPreferenceViewModel = SheardPreferenceViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_splash)
        Handler().postDelayed(Runnable {

            val islogIn = sheardPreferenceViewModel.loadData(Constants.IS_LOGIN)

            if (islogIn.equals("")) {
                val i: Intent = Intent(this, LoginActivity::class.java)
                startActivity(i)
                finish()
            }else{
                val i: Intent = Intent(this, MainActivity::class.java)
                startActivity(i)
                finish()
            }

        }, SPLASH_SCREEN_TIME_OUT.toLong())

    }
}