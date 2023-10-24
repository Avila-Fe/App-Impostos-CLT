package com.example.trabalhador.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.trabalhador.R
import com.example.trabalhador.databinding.FragmentSalaryBonusBinding
import com.example.trabalhador.model.BonusResult
import com.example.trabalhador.viewModel.SalaryBonusViewModel

class SalaryBonusFragment : Fragment() {

    private lateinit var binding: FragmentSalaryBonusBinding
    private lateinit var viewModel: SalaryBonusViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View {

        binding = FragmentSalaryBonusBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this)[SalaryBonusViewModel::class.java]

        viewModel.salaryResult.observe(viewLifecycleOwner, Observer { result ->
            updateValues(result)
        })

        binding.buttonCalculatePayment.setOnClickListener {
           calculate()
        }

        return binding.root
    }

    fun calculate(){
        if (validOk()){
            val salary = binding.textInputEditTextSalary.text.toString().toFloat()
            val monthWork = binding.textInputEditTextDayWork.text.toString().toInt()
            val dependents = binding.textInputEditTextDependent.text.toString().toInt()
            val extra = binding.textInputEditTextExtraHour.text.toString().toFloat()

            viewModel.calculatePayment(salary, monthWork, dependents, extra)
        } else{
            Toast.makeText(context, R.string.validation_fields, Toast.LENGTH_SHORT).show()
        }

    }

    private fun updateValues(salaryResult: BonusResult) {
        binding.textVFgts.text = "R$ ${"%.2f".format(salaryResult.fgts)}"
        binding.textVInss.text = "R$ ${"%.2f".format(salaryResult.inss)}"
        binding.textVIrpf.text = "R$ ${"%.2f".format(salaryResult.irpf)}"
        binding.textTotalSalary.text = "R$ ${"%.2f".format(salaryResult.singlePayment)}"
        binding.textValueFirst.text = "R$ ${"%.2f".format(salaryResult.firstPayment)}"
        binding.textValueSecond.text = "R$ ${"%.2f".format(salaryResult.secondPayment)}"
    }

    private fun validOk(): Boolean =(
            binding.textInputEditTextSalary.text.toString() != "" &&
                    binding.textInputEditTextSalary.text.toString().toFloat() != 0f &&
                    binding.textInputEditTextDayWork.text.toString() != "" &&
                    binding.textInputEditTextDayWork.text.toString().toInt() <= 12 &&
                    binding.textInputEditTextDayWork.text.toString().toInt() >= 1 &&
                    binding.textInputEditTextDependent.text.toString() != "" &&
                    binding.textInputEditTextDependent.text.toString().toInt() >= 0 &&
                    binding.textInputEditTextExtraHour.text.toString() != "")
}