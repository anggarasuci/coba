package com.example.coba.di

import com.example.coba.ui.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(
            getUserSearchUseCase = get()
        )
    }
}

val appModules = listOf(viewModelModule)