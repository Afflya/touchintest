package com.afflyas.touchintest.di

import com.afflyas.touchintest.ui.listmovies.ListMoviesFragment
import com.afflyas.touchintest.ui.movieinfo.MovieInfoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Module for fragments
 */
@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeListMoviesFragment() : ListMoviesFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieInfoFragment() : MovieInfoFragment

}