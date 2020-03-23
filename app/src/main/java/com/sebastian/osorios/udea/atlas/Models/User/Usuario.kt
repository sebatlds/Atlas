package com.sebastian.osorios.udea.atlas.Models.User

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
class Usuario (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "email") val email : String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "last_name") val lastName : String,
    @ColumnInfo(name = "password") val password : String,
    @ColumnInfo(name = "date") val date : String,
    @ColumnInfo(name = "gender") val gender : String
    )