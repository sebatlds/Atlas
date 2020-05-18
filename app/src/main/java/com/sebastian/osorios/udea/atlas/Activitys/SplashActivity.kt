package com.sebastian.osorios.udea.atlas.Activitys

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import com.sebastian.osorios.udea.atlas.R
import com.sebastian.osorios.udea.atlas.Util.CheckInternetConexion
import com.sebastian.osorios.udea.atlas.Util.CommonFunctions
import com.sebastian.osorios.udea.atlas.Util.Constants
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
        },3000)


    }


    private fun goToLandingActivity(){
        val intent = Intent(this,
            LandingActivity::class.java)
        startActivity(intent)
        finish()
    }
}