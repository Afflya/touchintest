package com.afflyas.touchintest.di

import android.app.Application
import androidx.room.Room
import com.afflyas.touchintest.api.ApiHelper
import com.afflyas.touchintest.api.ApiService
import com.afflyas.touchintest.db.AppDatabase
import com.afflyas.touchintest.db.MovieDao
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.google.gson.GsonBuilder



/**
 * Module to provide single Retrofit instance and ViewModel's modules
 */
@Module(includes = [ViewModelModule::class])
class AppModule {

    /**
     *
     * Provide single retrofit instance of [ApiService]
     *
     * @return instance of [ApiService]
     */
    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
                .baseUrl(ApiHelper.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(
                        /**
                         * add deserialization to java.sql.Date
                         */
                        GsonBuilder()
                        .setDateFormat("yyyy-MM-dd")
                        .create())
                )
                .build()
                .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase {
        return Room
                .databaseBuilder(app, AppDatabase::class.java, "touchintest.db")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(db: AppDatabase): MovieDao {
        return db.movieDao()
    }

}
