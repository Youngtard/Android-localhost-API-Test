package com.youngtard.localhostapitest.network

import com.youngtard.localhostapitest.model.Resp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface LocalHostAPIService {

    //Get resource (JSON Response) at specified URL path
    @GET("{path}")
    fun returnIndex(@Path("path") path: String): Call<Resp>

    @GET("/index/child")
    fun returnChild(): Call<Resp>

}