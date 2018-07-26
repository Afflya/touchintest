package com.afflyas.touchintest.api

object ApiHelper {

    const val BASE_URL = "https://raw.githubusercontent.com/Denis55ka/TouchinTest/master/"

    @JvmStatic
    fun getPosterUrl(posterPath: String): String{
        return BASE_URL + "images" + posterPath
    }

}