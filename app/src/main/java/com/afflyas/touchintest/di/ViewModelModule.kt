package com.afflyas.touchintest.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.afflyas.touchintest.ui.listmovies.ListMoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListMoviesViewModel::class)
    abstract fun bindListMoviesViewModel(listMoviesViewModel: ListMoviesViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}