package com.sebastian.osorios.udea.atlas.Models.Countries


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Currency(
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String
) : Serializable