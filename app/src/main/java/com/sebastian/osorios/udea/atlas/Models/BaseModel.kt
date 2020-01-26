package com.sebastian.osorios.udea.atlas.Models

import com.google.gson.annotations.SerializedName


data class BaseModel (
    @SerializedName("result") val result : List<User>,
    @SerializedName("_meta") val _meta : Meta
)

