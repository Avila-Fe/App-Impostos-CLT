package com.example.trabalhador.di

import com.example.trabalhador.MainActivity
import com.example.trabalhador.core.FragmentScope
import com.example.trabalhador.view.PaymentFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface PaymentComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): PaymentComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: PaymentFragment)
}