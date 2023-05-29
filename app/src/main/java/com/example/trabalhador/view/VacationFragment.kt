package com.example.trabalhador.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.trabalhador.R
import com.example.trabalhador.databinding.FragmentPaymentBinding
import com.example.trabalhador.databinding.FragmentVacationBinding

class VacationFragment : Fragment() {

    private lateinit var binding: FragmentVacationBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        binding = FragmentVacationBinding.inflate(layoutInflater)

        binding.buttonCalculatePayment.setOnClickListener {
            calculate()
        }

        return binding.root
    }

    private fun calculate(){

        if (validOk()){
        val salary = binding.textInputEditTextSalary.text.toString().toFloat()
        val daysVacation = binding.textInputEditTextDayVacation.text.toString().toInt()
        val daysSale = binding.textInputEditTextDaySale.text.toString().toInt()
        val extraValue = binding.textInputEditTextExtraHour.text.toString().toFloat()
        val dependents = binding.textInputEditTextDependents.text.toString().toInt()


        }else{
            Toast.makeText(context, R.string.validation_fields, Toast.LENGTH_SHORT).show()
        }

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

    private fun calculateIrpf(salary: Float, workDays: Int, earnings: Float, dependents: Int, ): Float {

        val valueBase: Float =
            (((salary / 30) * workDays) + earnings) - calculateInss(salary, workDays, earnings)
        var valueIrpf = 0f
        var valueDepents = dependents * 189.59f

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

    private fun handlerBalance(value: Float) {
        if (value <= 0) {
            binding.textTotalSalary.setTextColor(resources.getColor(R.color.red))
        } else if (value > 0) {
            binding.textTotalSalary.setTextColor(resources.getColor(R.color.green))
        }
    }

    private fun validOk(): Boolean = (
            binding.textInputEditTextSalary.text.toString() != "" &&
                    binding.textInputEditTextSalary.text.toString().toFloat() != 0f &&
                    binding.textInputEditTextDayVacation.text.toString() != "" &&
                    binding.textInputEditTextDayVacation.text.toString().toInt() <= 30 &&
                    binding.textInputEditTextDayVacation.text.toString().toInt() >= 3 &&
                    binding.textInputEditTextDaySale.text.toString() != "" &&
                    binding.textInputEditTextDaySale.text.toString().toInt() <= 10 &&
                    binding.textInputEditTextDaySale.text.toString().toInt() >= 0 &&
                    binding.textInputEditTextExtraHour.text.toString() != "" &&
                    binding.textInputEditTextExtraHour.text.toString().toFloat() >= 0f &&
                    binding.textInputEditTextDependents.text.toString() != "" &&
                    binding.textInputEditTextDependents.text.toString().toInt() >= 0)
}