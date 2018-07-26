package com.afflyas.touchintest.api


import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("api/movies.json")
    fun getMovies(): Call<ApiResponse>

}