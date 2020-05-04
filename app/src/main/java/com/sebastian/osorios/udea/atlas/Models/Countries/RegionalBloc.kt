package com.sebastian.osorios.udea.atlas.Models.Countries


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RegionalBloc(
    @SerializedName("acronym")
    val acronym: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("otherAcronyms")
    val otherAcronyms: List<Any>,
    @SerializedName("otherNames")
    val otherNames: List<String>
) : Serializable