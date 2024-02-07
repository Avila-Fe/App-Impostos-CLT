package com.example.trabalhador.core

import com.example.trabalhador.di.PaymentComponent
import dagger.Module
import dagger.Subcomponent

@Module(subcomponents = [PaymentComponent::class])
class AppSubcomponents