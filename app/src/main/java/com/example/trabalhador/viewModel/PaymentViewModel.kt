package com.example.trabalhador.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trabalhador.model.PaymentCalculator
import com.example.trabalhador.model.PaymentResult

class PaymentViewModel: ViewModel() {

    private val paymentCalculator = PaymentCalculator()

    private val _paymentResult = MutableLiveData<PaymentResult>()
    val paymentResult: LiveData<PaymentResult> get() = _paymentResult

    fun calculatePayment(salary: Float, workDays: Int, earnings: Float,
                         discount: Float, dependents: Int
    ){
       val result = paymentCalculator.calculatePayment(salary, workDays, earnings, discount, dependents)
        _paymentResult.value = result
    }
}