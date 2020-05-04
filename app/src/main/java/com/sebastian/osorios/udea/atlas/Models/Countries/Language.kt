package com.sebastian.osorios.udea.atlas.Models.Countries


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Language(
    @SerializedName("iso639_1")
    val iso6391: String,
    @SerializedName("iso639_2")
    val iso6392: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("nativeName")
    val nativeName: String
) : Serializable