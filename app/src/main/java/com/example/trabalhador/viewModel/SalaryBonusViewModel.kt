package com.example.trabalhador.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trabalhador.model.BonusResult
import com.example.trabalhador.model.PaymentCalculator
import com.example.trabalhador.model.PaymentResult
import com.example.trabalhador.model.SalaryBonusCalculator

class SalaryBonusViewModel: ViewModel(){

    private val salaryCalculator = SalaryBonusCalculator()

    private val _salaryResult = MutableLiveData<BonusResult>()
    val salaryResult: LiveData<BonusResult> get() = _salaryResult

    fun calculatePayment(salary: Float, workMonth: Int, dependents: Int, extra: Float
    ){
        val result = salaryCalculator.calculatePayment(salary, workMonth, dependents, extra)
        _salaryResult.value = result
    }

}