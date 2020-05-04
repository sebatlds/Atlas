package com.sebastian.osorios.udea.atlas.Models.Countries

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


class Countries  (
    @SerializedName("name") val name : String,
    @SerializedName("topLevelDomain") val topLevelDomain : List<String>,
    @SerializedName("alpha2Code") val alpha2Code : String,
    @SerializedName("alpha3Code") val alpha3Code : String,
    @SerializedName("capital") val capital : String,
    @SerializedName("altSpellings") val altSpellings : List<String>,
    @SerializedName("region") val region : String,
    @SerializedName("subregion") val subregion : String,
    @SerializedName("population") val population : Int,
    @SerializedName("latlng") val latlng : List<Double>,
    @SerializedName("demonym") val demonym : String,
    @SerializedName("area") val area : Double,
    @SerializedName("gini") val gini : Double,
    @SerializedName("timezones") val timezones : List<String>,
    @SerializedName("borders") val borders : List<String>,//implementado
    @SerializedName("nativeName") val nativeName : String,
    @SerializedName("callingCodes") val callingCodes : List<String>,
    @SerializedName("currencies") val currencies : List<Currency>,//implementado
    @SerializedName("languages") val languages : List<Language>,//implemntado
    @SerializedName("translations") val translations : Translations,
    @SerializedName("flag") val flag : String,
    @SerializedName("regionalBlocs") val regionalBlocs : List<RegionalBloc>,//implementado
    @SerializedName("cioc") val cioc : String
) : Serializable


