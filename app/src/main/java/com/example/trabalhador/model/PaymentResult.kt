package com.example.trabalhador.model

data class PaymentResult(
    val fgts: Float,
    val inss: Float,
    val irpf: Float,
    val base: Float,
    val totalSalary: Float
)