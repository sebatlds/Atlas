package com.sebastian.osorios.udea.atlas.Interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sebastian.osorios.udea.atlas.Models.User.Usuario

@Dao
interface UserDAO {

    @Insert
    fun insertUser(usuario: Usuario)

    @Query("SELECT * FROM user_table WHERE email LIKE :email")
    fun searchUser(email: String): Usuario

    @Query("SELECT * FROM user_table WHERE id LIKE :id")
    fun searchUserId(id: Int): Usuario

    @Update
    fun updateUser(usuario: Usuario)
}