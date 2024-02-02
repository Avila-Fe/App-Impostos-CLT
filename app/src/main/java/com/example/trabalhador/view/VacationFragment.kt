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
import com.example.trabalhador.databinding.FragmentPaymentBinding
import com.example.trabalhador.databinding.FragmentVacationBinding
import com.example.trabalhador.model.VacationResult
import com.example.trabalhador.util.KeyboardUtils
import com.example.trabalhador.util.handlerUtils
import com.example.trabalhador.viewModel.VacationViewModel

class VacationFragment : Fragment(R.layout.fragment_vacation) {

    private lateinit var binding: FragmentVacationBinding
    private lateinit var viewModel: VacationViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View {
        viewModel = ViewModelProvider(this)[VacationViewModel::class.java]
        binding = FragmentVacationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObservables()

        with(binding) {
            radioNot.isChecked = true
            buttonCalculatePayment.setOnClickListener {
                calculateVacation()
                KeyboardUtils.hideKeyboard(this@VacationFragment)
            }
        }
    }

    private fun initObservables() {
        viewModel.vacationResult.observe(viewLifecycleOwner) { result ->
            updateValues(result)
        }
    }

    private fun calculateVacation() {
        if (validOk()) {
            val salary = binding.textInputEditTextSalary.text.toString().toFloat()
            val daysVacation = binding.textInputEditTextDayVacation.text.toString().toInt()
            val extraValue = binding.textInputEditTextExtraHour.text.toString().toFloat()
            val dependents = binding.textInputEditTextDependent.text.toString().toInt()
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
    }

    private fun verifySale(): Int{
        return if (binding.radioYes.isChecked) 1 else 0
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