package com.afflyas.touchintest.ui.listmovies

import androidx.recyclerview.widget.DiffUtil
import com.afflyas.touchintest.db.MovieEntity

class MoviesDiffUtilsCallback(private val oldMoviesList: List<MovieEntity>, private val newMoviesList: List<MovieEntity>) : DiffUtil.Callback() {


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldMoviesList[oldItemPosition].id == newMoviesList[newItemPosition].id
    }

    override fun getOldListSize(): Int {
        return oldMoviesList.size
    }

    override fun getNewListSize(): Int {
        return newMoviesList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldMoviesList[oldItemPosition] == newMoviesList[newItemPosition]
    }

}