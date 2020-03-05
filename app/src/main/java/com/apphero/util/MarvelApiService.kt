package com.apphero.util

import com.apphero.dataclass.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApiService {

    @GET("v1/public/characters")
    fun getMarvelHeroes(
        @Query("ts") timestamp: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ): Call<ApiResponse>

    @GET("v1/public/characters")
    fun getMarvelHeroesOffset(
        @Query("ts") timestamp: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): Call<ApiResponse>
}