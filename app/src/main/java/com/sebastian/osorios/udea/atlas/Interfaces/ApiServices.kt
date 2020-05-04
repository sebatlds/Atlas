package com.sebastian.osorios.udea.atlas.Interfaces


import com.sebastian.osorios.udea.atlas.Models.Countries.Countries
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @GET("/rest/v2/all")
    fun getCountries(): Call<List<Countries>>

}