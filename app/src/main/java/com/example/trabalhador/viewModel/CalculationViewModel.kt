package com.example.trabalhador.viewModel

import androidx.lifecycle.ViewModel

class CalculationViewModel : ViewModel(){
     fun calculateInss(totalBase: Float): Float {
        var valueInss = 0f

        if (totalBase <= 1320f) {
            valueInss = totalBase * 0.075f
        } else if (totalBase > 1320f && totalBase <= 2571.30f) {
            valueInss = (totalBase * 0.09f) - 19.800f
        } else if (totalBase > 2571.30f && totalBase <= 3856.94f) {
            valueInss = (totalBase * 0.12f) - 96.668f
        } else if (totalBase > 3856.94f && totalBase <= 7507.49f) {
            valueInss = (totalBase * 0.14f) - 173.806f
        }

        return valueInss
    }

     fun calculateIrpf(totalBase: Float, dependents: Int): Float {
        val valueDepents = dependents * 189.59f
        var valueIrpf = 0f

        if (totalBase > 2112.01f && totalBase <= 2826.65f) {
            valueIrpf = ((totalBase * 0.075f) - 158.40f) - valueDepents
        } else if (totalBase > 2826.66f && totalBase <= 3751.05f) {
            valueIrpf = ((totalBase * 0.15f) - 370.40f) - valueDepents
        } else if (totalBase > 3751.06f && totalBase <= 4664.68f) {
            valueIrpf = ((totalBase * 0.225f) - 651.73f) - valueDepents
        } else if (totalBase >= 4664.68) {
            valueIrpf = ((totalBase * 0.225f) - 884.96f) - valueDepents
        }

        return if (valueIrpf >= 0f) valueIrpf else 0f
    }
}