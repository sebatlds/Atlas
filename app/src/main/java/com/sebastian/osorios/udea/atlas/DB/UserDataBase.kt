package com.sebastian.osorios.udea.atlas.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sebastian.osorios.udea.atlas.Interfaces.UserDAO
import com.sebastian.osorios.udea.atlas.Models.User.Usuario

@Database(entities = [Usuario::class], version = 1)
abstract class UserDataBase : RoomDatabase() {
    abstract fun UserDAO(): UserDAO
}