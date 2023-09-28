package com.example.trabalhador.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.trabalhador.R
import com.example.trabalhador.databinding.FragmentVacationBinding
import com.example.trabalhador.model.VacationResult
import com.example.trabalhador.viewModel.VacationViewModel

class VacationFragment : Fragment() {

    private lateinit var binding: FragmentVacationBinding
    private lateinit var viewModel: VacationViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentVacationBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[VacationViewModel::class.java]

        viewModel.vacationResult.observe(viewLifecycleOwner, Observer { result ->
            updateValues(result)
        })

        binding.radioNot.isChecked = true
        binding.buttonCalculatePayment.setOnClickListener {
            calculateVacation()
        }

        return binding.root
    }

    private fun calculateVacation() {
        val salaryText = binding.textInputEditTextSalary.text.toString()
        val daysVacationText = binding.textInputEditTextDayVacation.text.toString()
        val extraValueText = binding.textInputEditTextExtraHour.text.toString()
        val dependentsText = binding.textInputEditTextDependent.text.toString()
        val hasSale = binding.radioYes.isChecked

        if (validOk(salaryText, daysVacationText, extraValueText, dependentsText)) {
            val salary = salaryText.toFloat()
            val daysVacation = daysVacationText.toInt()
            val extraValue = extraValueText.toFloat()
            val dependents = dependentsText.toInt()

            viewModel.calculateVacation(salary, daysVacation, extraValue, dependents, hasSale)

        } else {
            Toast.makeText(context, R.string.validation_fields, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateValues(vacationResult: VacationResult) {
        binding.textVBase.text = "R$ ${"%.2f".format(vacationResult.base)}"
        binding.textVExtra.text = "R$ ${"%.2f".format(vacationResult.extra)}"
        binding.textVInss.text = "R$ ${"%.2f".format(vacationResult.inss)}"
        binding.textVIrpf.text = "R$ ${"%.2f".format(vacationResult.irpf)}"
        binding.textTotalSalary.text = "R$ ${"%.2f".format(vacationResult.totalSalary)}"

        handlerBalance(vacationResult.totalSalary)
    }

    private fun handlerBalance(value: Float) {
        if (value <= 0) {
            binding.textTotalSalary.setTextColor(resources.getColor(R.color.red))
        } else if (value > 0) {
            binding.textTotalSalary.setTextColor(resources.getColor(R.color.green))
        }
    }

    private fun validOk(
        salaryText: String,
        daysVacationText: String,
        extraValueText: String,
        dependentsText: String,
    ): Boolean {
        return (salaryText.isNotEmpty() &&
                salaryText.toFloat() != 0f &&
                daysVacationText.isNotEmpty() &&
                daysVacationText.toInt() >= 3 &&
                daysVacationText.toInt() <= 30 &&
                extraValueText.isNotEmpty() &&
                extraValueText.toFloat() >= 0f &&
                dependentsText.isNotEmpty())
    }
}