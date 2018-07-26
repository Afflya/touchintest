package com.afflyas.touchintest.ui.common

import com.afflyas.touchintest.db.MovieEntity

/**
 * Interface for movie list item to open movie info fragment
 */
interface ItemClickCallback {
    fun onItemClick(movieEntity: MovieEntity)
}