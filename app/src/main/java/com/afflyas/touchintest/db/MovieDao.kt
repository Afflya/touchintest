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

    @Query("SELECT * FROM " + MovieEntity.TABLE_NAME)
    fun selectAll(): List<MovieEntity>

    @Query("SELECT * FROM " + MovieEntity.TABLE_NAME + " ORDER BY " + MovieEntity.COLUMN_VOTE_AVERAGE + " DESC ")
    fun selectSortedByRating(): List<MovieEntity>

    @Query("SELECT * FROM " + MovieEntity.TABLE_NAME + " ORDER BY " + MovieEntity.COLUMN_RELEASE_DATE + " DESC ")
    fun selectSortedByDate(): List<MovieEntity>



}