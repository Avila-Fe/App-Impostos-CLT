package com.example.trabalhador.di

import com.example.trabalhador.model.PaymentCalculatorUseCase
import com.example.trabalhador.usecase.PaymentCalculator
import dagger.Binds
import dagger.Module

@Module
abstract class UseCaseModule {
    @Binds
    abstract fun providePaymentCalculatorUseCase(
        useCase: PaymentCalculator
    ): PaymentCalculatorUseCase
}