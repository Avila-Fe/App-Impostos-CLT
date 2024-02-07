package com.example.trabalhador.core

import android.content.Context
import com.example.trabalhador.MainActivity
import com.example.trabalhador.di.PaymentComponent
import com.example.trabalhador.di.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class, AppSubcomponents::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun paymentComponent(): PaymentComponent.Factory

    fun inject(activity: MainActivity)
}