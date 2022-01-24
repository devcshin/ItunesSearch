package com.devc.watchacode.data.remote

import com.devc.watchacode.data.remote.dto.ItunesDto
import retrofit2.http.GET

interface ItunesApi {
    @GET("/search?term=greenday&entity=song")
    suspend fun getList() : ItunesDto
}