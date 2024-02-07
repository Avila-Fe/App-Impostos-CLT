package com.example.trabalhador.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trabalhador.core.FragmentScope
import com.example.trabalhador.model.PaymentCalculatorUseCase
import com.example.trabalhador.model.PaymentResult
import javax.inject.Inject

@FragmentScope
class PaymentViewModel @Inject constructor(
    private val paymentCalculatorUseCase: PaymentCalculatorUseCase
): ViewModel() {

    private val _paymentResult = MutableLiveData<PaymentResult>()
    val paymentResult: LiveData<PaymentResult> get() = _paymentResult

    fun calculatePayment(salary: Float, workDays: Int, earnings: Float,
                         discount: Float, dependents: Int
    ){
       val result = paymentCalculatorUseCase.calculatePayment(salary, workDays, earnings, discount, dependents)
        _paymentResult.value = result
    }
}