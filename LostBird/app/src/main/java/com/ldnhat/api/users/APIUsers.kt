package com.ldnhat.api.users

import com.ldnhat.model.Users
import retrofit2.Call
import retrofit2.http.*

interface APIUsers {

    @POST("users")
    fun save(@Body users: Users) : Call<Users>

    @POST("users")
    fun login(@Body users: Users) : Call<Users>

    @GET("users")
    fun findOne(@Query("userId") userId : Int, @Query("message") message : String)
        : Call<Users>
}