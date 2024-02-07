package com.example.trabalhador.model

interface PaymentCalculatorUseCase {

    fun calculatePayment(
        salary: Float, workDays: Int, earnings: Float,
        discount: Float, dependents: Int,
    ): PaymentResult
}


