package com.ldnhat.api.likes

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Query

interface APILike {

    @DELETE("likes")
    fun deleteLike(@Query("message") message : String, @Query("tweetId") tweetId : Int) :
            Call<Void>

    @POST("likes")
    fun saveByUserIdAndTweetId(@Query("message") message: String, @Query("userId") userId : Int,
        @Query("tweetId") tweetId : Int) : Call<Void>

}