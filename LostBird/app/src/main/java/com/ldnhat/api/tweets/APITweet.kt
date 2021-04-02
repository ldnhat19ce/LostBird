package com.ldnhat.api.tweets

import com.ldnhat.model.Tweet
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APITweet{

    @GET("tweets")
    fun tweet(@Query("userId") userId : Int, @Query("limit") limit : Int)
        : Call<Tweet>
}