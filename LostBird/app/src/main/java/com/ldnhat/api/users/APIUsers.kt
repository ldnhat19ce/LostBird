package com.ldnhat.api.users

import com.ldnhat.model.Users
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIUsers {

    @POST("users")
    fun save(@Body users: Users) : Call<Users>
}