package com.afflyas.touchintest.repository

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.afflyas.touchintest.api.ApiResponse
import com.afflyas.touchintest.api.ApiService
import com.afflyas.touchintest.core.App
import com.afflyas.touchintest.db.MovieDao
import com.afflyas.touchintest.db.MovieEntity
import com.afflyas.touchintest.ui.common.SortResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors
import javax.inject.Inject

/**
 *
 * Repository that handles MovieEntity objects.
 *
 * Request result stores in searchResult
 * that should be observed by ViewModel
 *
 * Result contains RepoResponseStatus with status of request
 * and List<MovieEntity> with the list of albums found
 *
 *
 * Api response data writes to the database (old data deletes)
 * and to searchResult
 *
 * if Request failed searchResult is read from the database
 *
 */
class MoviesRepository @Inject constructor(
        private val apiService: ApiService,
        private val movieDao: MovieDao) {

    var searchResult = MutableLiveData<RepoResponse<List<MovieEntity>>>()

    var sortBy: SortResponse = SortResponse.DEFAULT

    /**
     *
     * Load data from network or database
     *
     */
    fun loadMovies(sortBy: SortResponse){

        this.sortBy = sortBy

        searchResult.postValue(RepoResponse.loading())

        apiService.getMovies().enqueue(responseCallback)
    }

    /**
     *
     * Load data from database only
     *
     */
    fun loadMoviesFromDb(sortBy: SortResponse){

        this.sortBy = sortBy

        searchResult.postValue(RepoResponse.loading())

        Executors.newSingleThreadExecutor().execute {
            searchResult.postValue(RepoResponse.success(selectFromDb(sortBy)))
        }
    }

    private val responseCallback = object : Callback<ApiResponse> {
        /**
         *
         * Replace data in database with response result or delete all data if response result is empty
         *
         */
        override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
            Log.d(App.DEV_TAG, javaClass.simpleName + " onResponse")
            val responseBody = response.body()

            if (responseBody == null || responseBody.results.isEmpty()) {
                Executors.newSingleThreadExecutor().execute {
                    movieDao.deleteAll()
                    searchResult.postValue(RepoResponse.success(null))
                }
            } else {
                Executors.newSingleThreadExecutor().execute {
                    movieDao.deleteAll()
                    movieDao.insertAll(responseBody.results)
                    searchResult.postValue(RepoResponse.success(selectFromDb(sortBy)))
                }
            }


        }
        /**
         *
         * Trying to load from DB if request failed
         *
         */
        override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
            Log.d(App.DEV_TAG, javaClass.simpleName + " onFailure")
            Executors.newSingleThreadExecutor().execute {
                searchResult.postValue(RepoResponse.error(selectFromDb(sortBy)))
            }
        }
    }

    /**
     *
     * Load sorted data from db
     *
     */
    @WorkerThread
    fun selectFromDb(sortBy: SortResponse): List<MovieEntity>?{

        val list: List<MovieEntity> = when(sortBy){
            SortResponse.DEFAULT -> movieDao.selectAll()
            SortResponse.RATING -> movieDao.selectSortedByRating()
            SortResponse.DATE -> movieDao.selectSortedByDate()
        }

        return if(list.isEmpty()){
            null
        }else{
            list
        }
    }
}