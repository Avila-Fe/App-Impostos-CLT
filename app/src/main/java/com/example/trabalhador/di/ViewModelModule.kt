package com.example.trabalhador.di

import com.example.trabalhador.viewModel.PaymentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(PaymentViewModel::class)
    abstract
}