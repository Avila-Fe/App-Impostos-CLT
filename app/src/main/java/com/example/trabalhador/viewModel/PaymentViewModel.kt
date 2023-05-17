package com.example.trabalhador.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PaymentViewModel : ViewModel() {

    private val _valueSalary = MutableLiveData<String>()
    val valueSalary: LiveData<String> = _valueSalary


}