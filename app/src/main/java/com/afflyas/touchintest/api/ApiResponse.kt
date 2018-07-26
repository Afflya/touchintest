package com.afflyas.touchintest.api

import com.afflyas.touchintest.db.MovieEntity
import com.google.gson.annotations.SerializedName

/**
 * Response data returned by Api
 */
data class ApiResponse(@SerializedName("results") val results: List<MovieEntity>)