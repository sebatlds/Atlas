package com.sebastian.osorios.udea.atlas.Models
import com.google.gson.annotations.SerializedName

data class Meta (

    @SerializedName("success") val success : Boolean,
    @SerializedName("code") val code : Int,
    @SerializedName("message") val message : String,
    @SerializedName("totalCount") val totalCount : Int,
    @SerializedName("pageCount") val pageCount : Int,
    @SerializedName("currentPage") val currentPage : Int,
    @SerializedName("perPage") val perPage : Int
)