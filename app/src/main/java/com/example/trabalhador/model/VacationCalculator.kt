package com.example.trabalhador.model

class VacationCalculator {
    fun calculateVacation(
        salary: Float,
        daysVacation: Int,
        extraValue: Float,
        dependents: Int,
        hasSale: Int,
    ): VacationResult {

        val valueSale = verifySale(hasSale, daysVacation, salary)
        val totalBase = valueSale + extraValue
        val totalExtra = valueSale / 3

        val inss = calculateInss(totalBase, totalExtra, extraValue)
        val irpf = calculateIrpf(totalBase, totalExtra, dependents, extraValue)
        val salaryLiquid = (totalBase - inss - irpf) + totalExtra

        return VacationResult(totalBase, totalExtra, inss, irpf, salaryLiquid)
    }

    private fun calculateInss(totalBase: Float, totalExtra: Float, extraValue: Float): Float {

        val valueBase = totalBase + totalExtra + extraValue
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

    private fun calculateIrpf(totalBase: Float, totalExtra: Float, dependents: Int, extraValue: Float): Float {

        val valueBase: Float = (totalBase + totalExtra) - calculateInss(totalBase, totalExtra, extraValue)
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

    private fun verifySale(hasSale: Int, daysVacation: Int, salary: Float): Float{
        val daysSale:Int
        val baseSale:Float

        // var daysBaseSalary = 0
        var baseSalary = salary / 30

        if (hasSale == 1) {
            daysSale = daysVacation / 3
            baseSale = daysSale.toFloat() * baseSalary

            baseSalary = (baseSalary * daysVacation) + baseSale
        } else {
            baseSalary *= daysVacation
        }

        return baseSalary
    }
}

data class VacationResult(
    val base: Float,
    val extra: Float,
    val inss: Float,
    val irpf: Float,
    val totalSalary: Float,
)