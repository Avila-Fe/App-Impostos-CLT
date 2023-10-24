package com.example.trabalhador.model

class SalaryBonusCalculator {

    fun calculatePayment(
        salary: Float,
        workMonth: Int,
        dependents: Int,
        extra: Float,
    ): SalaryResult {

        val totalBase = ((salary + extra) / 12) * workMonth
        val fgts = (salary + extra) * 0.08f
        val inss = calculateInss(totalBase)
        val irpf = calculateIrpf(totalBase, dependents)

        val singlePayment = 0f
        val firstPayment = 0f
        val secondPayment = 0f

        return SalaryResult(fgts, inss, irpf, singlePayment, firstPayment, secondPayment)
    }

    private fun calculateInss(salary: Float): Float {

        var valueInss = 0f

        if (salary <= 1320f) {
            valueInss = salary * 0.075f
        } else if (salary > 1320f && salary <= 2571.30f) {
            valueInss = (salary * 0.09f) - 19.800f
        } else if (salary > 2571.30f && salary <= 3856.94f) {
            valueInss = (salary * 0.12f) - 96.668f
        } else if (salary > 3856.94f && salary <= 7507.49f) {
            valueInss = (salary * 0.14f) - 173.806f
        }

        return valueInss
    }

    private fun calculateIrpf(salary: Float, dependents: Int): Float {

        val valueBase = salary - calculateInss(salary)
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

data class SalaryResult(
    val fgts: Float, val inss: Float, val irpf: Float,
    val singlePayment: Float, val firstPayment: Float, val secondPayment: Float,
)