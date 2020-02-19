package com.sebastian.osorios.udea.atlas.Interfaces


import com.sebastian.osorios.udea.atlas.Models.User.BaseModel
import com.sebastian.osorios.udea.atlas.Models.Countries.Countries
import com.sebastian.osorios.udea.atlas.Models.User.UserPost
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @Headers("Content-Type: application/json", "Authorization: Bearer _sMXnp5LE3fzlALHfcbv_OFG5_cqN93HKxod")
    @GET("/public-api/users")
    fun getUser(@Query("email")userEmail : String): Call<BaseModel>



    @Headers("Content-Type: application/json","Authorization: Bearer _sMXnp5LE3fzlALHfcbv_OFG5_cqN93HKxod")
    @POST("/public-api/users")
    fun createUser(@Body user: UserPost): Call<BaseModel>

    @GET("/rest/v2/all")
    fun getCountries(): Call<List<Countries>>

}