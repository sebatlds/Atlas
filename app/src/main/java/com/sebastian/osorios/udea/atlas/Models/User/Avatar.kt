package com.sebastian.osorios.udea.atlas.Models.User

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table_image")
data class Avatar (
    @PrimaryKey @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "image") var image : String
)