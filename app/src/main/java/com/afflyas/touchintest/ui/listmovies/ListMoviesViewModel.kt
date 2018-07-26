package com.afflyas.touchintest.ui.listmovies

import androidx.lifecycle.ViewModel
import com.afflyas.touchintest.repository.MoviesRepository
import com.afflyas.touchintest.ui.common.SortResponse
import javax.inject.Inject

class ListMoviesViewModel @Inject constructor(private val moviesRepository: MoviesRepository) : ViewModel() {

    val searchResult = moviesRepository.searchResult

    var sortBy: SortResponse = SortResponse.DEFAULT

    fun loadMovies() {
        moviesRepository.loadMovies(sortBy)
    }

    fun loadMoviesFromDb(){
        moviesRepository.loadMoviesFromDb(sortBy)
    }

}

