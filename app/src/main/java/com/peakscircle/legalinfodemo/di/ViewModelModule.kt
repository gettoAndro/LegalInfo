package com.peakscircle.legalinfodemo.di

import com.peakscircle.legalinfodemo.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel()
    }
}