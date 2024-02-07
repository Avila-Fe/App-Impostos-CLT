package com.example.trabalhador.usecase

import com.example.trabalhador.model.PaymentCalculatorUseCase
import com.example.trabalhador.model.PaymentResult
import javax.inject.Inject

class PaymentCalculator @Inject constructor() : PaymentCalculatorUseCase {

    override fun calculatePayment(
    salary: Float, workDays: Int, earnings: Float,
    discount: Float, dependents: Int,
    ): PaymentResult {
        val salary = salary
        val totalBase = ((salary / 30) * workDays) + earnings
        val fgts = totalBase * 0.08f
        val inss = calculateInss(totalBase, workDays, earnings)
        val irpf = calculateIrpf(salary, workDays, earnings, dependents)
        val salaryLiquid = (totalBase - discount - inss - irpf) + earnings

        return PaymentResult(fgts, inss, irpf, totalBase, salaryLiquid)
    }
    private fun calculateInss(salary: Float, workDays: Int, earnings: Float): Float {

        val valueBase: Float = ((salary / 30) * workDays) + earnings
        var valueInss = 0f

        if (valueBase <= 1320f) {
            valueInss = valueBase * 0.075f
        } else if (valueBase > 1320f && valueBase <= 2571.30f) {
            valueInss = (valueBase * 0.09f) - 19.800f
        } else if (valueBase > 2571.30f && valueBase <= 3856.94f) {
            valueInss = (valueBase * 0.12f) - 96.668f
        } else if (valueBase > 3856.94f && valueBase <= 7507.49f) {
            valueInss = (valueBase * 0.14f) - 173.806f
        }

        return valueInss
    }

    private fun calculateIrpf(salary: Float, workDays: Int, earnings: Float, dependents: Int,
    ): Float {

        val valueBase: Float =
            (((salary / 30) * workDays) + earnings) - calculateInss(salary, workDays, earnings)
        var valueIrpf = 0f
        val valueDepents = dependents * 189.59f

        if (valueBase > 2112.01f && valueBase <= 2826.65f) {
            valueIrpf = ((valueBase * 0.075f) - 158.40f) - valueDepents
        } else if (valueBase > 2826.66f && valueBase <= 3751.05f) {
            valueIrpf = ((valueBase * 0.15f) - 370.40f) - valueDepents
        } else if (valueBase > 3751.06f && valueBase <= 4664.68f) {
            valueIrpf = ((valueBase * 0.225f) - 651.73f) - valueDepents
        } else if (valueBase >= 4664.68) {
            valueIrpf = ((valueBase * 0.225f) - 884.96f) - valueDepents
        }

        return if (valueIrpf >= 0f) valueIrpf
        else 0f

    }
}