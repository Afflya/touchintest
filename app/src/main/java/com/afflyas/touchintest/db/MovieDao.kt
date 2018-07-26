package com.afflyas.touchintest.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<MovieEntity>)

    @Query("DELETE FROM " + MovieEntity.TABLE_NAME)
    fun deleteAll()

    /**
     *
     * get all movies
     *
     */
    @Query("SELECT * FROM " + MovieEntity.TABLE_NAME)
    fun selectAll(): List<MovieEntity>

    /**
     *
     * get movie list sorted by rating (best to worst)
     *
     */
    @Query("SELECT * FROM " + MovieEntity.TABLE_NAME + " ORDER BY " + MovieEntity.COLUMN_VOTE_AVERAGE + " DESC ")
    fun selectSortedByRating(): List<MovieEntity>

    /**
     *
     * get movie list sorted by date (new to old)
     *
     */
    @Query("SELECT * FROM " + MovieEntity.TABLE_NAME + " ORDER BY " + MovieEntity.COLUMN_RELEASE_DATE + " DESC ")
    fun selectSortedByDate(): List<MovieEntity>



}