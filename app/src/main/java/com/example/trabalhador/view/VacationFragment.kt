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

class VacationFragment : Fragment(R.layout.fragment_vacation) {

    private lateinit var binding: FragmentVacationBinding
    private lateinit var viewModel: VacationViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewModel = ViewModelProvider(this)[VacationViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObservables()

        with(binding) {
            radioNot.isChecked = true
            buttonCalculatePayment.setOnClickListener {
                calculateVacation()
            }
        }
    }

    private fun initObservables() {
        viewModel.vacationResult.observe(viewLifecycleOwner) { result ->
            updateValues(result)
        }
    }

    private fun calculateVacation() {
        val salaryText = binding.textInputEditTextSalary.text.toString()
        val daysVacationText = binding.textInputEditTextDayVacation.text.toString()
        val extraValueText = binding.textInputEditTextExtraHour.text.toString()
        val dependentsText = binding.textInputEditTextDependent.text.toString()

        if (validOk()) {
            val salary = salaryText.toFloat()
            val daysVacation = daysVacationText.toInt()
            val extraValue = extraValueText.toFloat()
            val dependents = dependentsText.toInt()
            val hasSale = verifySale()

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

    private fun verifySale(): Int{
        return if (binding.radioYes.isChecked) 1 else 0
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
                    binding.textInputEditTextExtraHour.text.toString() != "" &&
                    binding.textInputEditTextExtraHour.text.toString().toFloat() >= 0f &&
                    binding.textInputEditTextDependent.text.toString() != "" &&
                    binding.textInputEditTextDependent.text.toString().toInt() >= 0)
}