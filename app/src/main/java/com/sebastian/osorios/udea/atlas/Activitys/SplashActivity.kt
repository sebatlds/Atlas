package com.sebastian.osorios.udea.atlas.Activitys

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.sebastian.osorios.udea.atlas.R
import java.util.*
import kotlin.concurrent.timerTask

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setContentView(R.layout.activity_splash)

        val time = Timer()
        time.schedule(timerTask {
            goToLandingActivity()
        },2000
        )
    }


    private fun goToLandingActivity(){
        val intent = Intent(this,
            LandingActivity::class.java)
        startActivity(intent)
        finish()
    }
}