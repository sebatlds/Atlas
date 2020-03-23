package com.sebastian.osorios.udea.atlas.DB

import android.app.Application
import androidx.room.Room

class SesionRoom : Application() {
    companion object{
        lateinit var database: UserDataBase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            this,
            UserDataBase::class.java,
            "user_DB"
        ).allowMainThreadQueries()
            .build()
    }
}