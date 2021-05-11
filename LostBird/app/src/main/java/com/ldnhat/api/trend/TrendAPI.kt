package com.ldnhat.api.trend

import com.ldnhat.model.Trend
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TrendAPI {

    @GET("trends")
    fun findAll(@Query("message") message : String) : Call<MutableList<Trend>>
}