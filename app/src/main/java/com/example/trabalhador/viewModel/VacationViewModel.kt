package com.example.trabalhador.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trabalhador.model.VacationCalculator
import com.example.trabalhador.model.VacationResult

class VacationViewModel : ViewModel() {
    private val vacationCalculator = VacationCalculator()

    private val _vacationResult = MutableLiveData<VacationResult>()
    val vacationResult: LiveData<VacationResult> get() = _vacationResult

    fun calculateVacation(
        salary: Float,
        daysVacation: Int,
        extraValue: Float,
        dependents: Int,
        hasSale: Int
    ) {
        val result = vacationCalculator.calculateVacation(salary, daysVacation, extraValue, dependents, hasSale)
        _vacationResult.value = result
    }
}