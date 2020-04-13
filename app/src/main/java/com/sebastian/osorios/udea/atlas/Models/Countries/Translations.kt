package com.sebastian.osorios.udea.atlas.Models.Countries

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


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
) : Serializable