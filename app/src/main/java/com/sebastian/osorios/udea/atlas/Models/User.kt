package com.sebastian.osorios.udea.atlas.Models

import com.google.gson.annotations.SerializedName


data class User (

    @SerializedName("id") val id : Int,
    @SerializedName("first_name") val firstName : String,
    @SerializedName("last_name") val lastName : String,
    @SerializedName("gender") val gender : String,
    @SerializedName("dob") val date : String,
    @SerializedName("email") val email : String,
    @SerializedName("phone") val country : String,
    @SerializedName("address") val password : String,
    @SerializedName("status") val status : String,
    @SerializedName("_links") val _links : Links,
    @SerializedName("field") val field : String,
    @SerializedName("message") val message : String
)

data class UserPost(
    @SerializedName("first_name") val firstName : String,
    @SerializedName("last_name") val lastName : String,
    @SerializedName("gender") val gender : String,
    @SerializedName("dob") val date : String,
    @SerializedName("email") val email : String,
    //@SerializedName("phone") val country : String,
    @SerializedName("address") val password : String,
    @SerializedName("status") val status : String
)