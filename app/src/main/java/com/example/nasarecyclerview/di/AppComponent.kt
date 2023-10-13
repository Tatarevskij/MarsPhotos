package com.example.nasarecyclerview.di

import com.example.nasarecyclerview.presentation.MainViewModelFactory
import dagger.Component

@Component
interface AppComponent {
    fun mainViewModelFactory(): MainViewModelFactory
}