package com.ldnhat.api.tweets

import com.ldnhat.model.Tweet
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface APITweet{

    @GET("tweets")
    fun tweet(@Query("userId") userId : Int, @Query("message") message : String)
        : Call<MutableList<Tweet>>



    @Multipart
    @POST("tweets")
    fun saveFileTweet(@Part files : MutableList<MultipartBody.Part>,
                      @Query("message") upload : String, @Query("tweetId") id : Int) : Call<Void>

    @POST("tweets")
    fun saveTweet(@Body tweet: Tweet, @Query("message") message : String) : Call<Tweet>
}