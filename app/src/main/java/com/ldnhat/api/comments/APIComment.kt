package com.ldnhat.api.comments

import com.ldnhat.model.Comment
import retrofit2.Call
import retrofit2.http.*

interface APIComment {

    @GET("comments")
    fun findByTweetId(@Query("tweetId") tweetId:Int, @Query("message") message : String) :
            Call<MutableList<Comment>>

    @POST("comments")
    fun saveComment(@Body comment: Comment) : Call<MutableList<Comment>>

    @DELETE("comments")
    fun deleteComment(@Query("message") message : String, @Query("commentId") commentId : Int,
        @Query("tweetId") tweetId : Int) :
            Call<MutableList<Comment>>
}