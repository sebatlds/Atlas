package com.sebastian.osorios.udea.atlas.Models.User

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
class Usuario(
    @PrimaryKey @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "lastName") var lastName: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "gender") var gender: String,
    @ColumnInfo(name = "image") var image: String?
)