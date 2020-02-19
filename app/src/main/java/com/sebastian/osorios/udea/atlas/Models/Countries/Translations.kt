package com.sebastian.osorios.udea.atlas.Models.Countries

import com.google.gson.annotations.SerializedName
data class Translations (
    @SerializedName("de") val aleman: String,
    @SerializedName("es") val espanish : String,
    @SerializedName("fr") val frances : String,
    @SerializedName("ja") val japones : String,
    @SerializedName("it") val italiano : String,
    @SerializedName("br") val breton : String,
    @SerializedName("pt") val portugues : String,
    @SerializedName("nl") val holandes : String,
    @SerializedName("hr") val croata : String,
    @SerializedName("fa") val persa : String
)