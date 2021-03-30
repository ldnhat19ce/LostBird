package com.ldnhat.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BaseConfig {

    lateinit var retrofit: Retrofit

    fun getInstance() : Retrofit{

        retrofit = Retrofit.Builder().baseUrl("http:/192.168.1.9:8080/DemoJson_war_exploded/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        return retrofit
    }
}