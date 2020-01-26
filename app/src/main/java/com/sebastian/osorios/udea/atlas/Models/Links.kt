package com.sebastian.osorios.udea.atlas.Models

import com.google.gson.annotations.SerializedName

data class Links (
    @SerializedName("self") val self : Self,
    @SerializedName("edit") val edit : Edit,
    @SerializedName("avatar") val avatar : Avatar
)