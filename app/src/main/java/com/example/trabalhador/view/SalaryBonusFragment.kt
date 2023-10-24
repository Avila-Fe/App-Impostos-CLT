package com.example.trabalhador.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.trabalhador.R
import com.example.trabalhador.databinding.FragmentSalaryBonusBinding
import com.example.trabalhador.databinding.FragmentVacationBinding
import com.example.trabalhador.model.SalaryResult
import com.example.trabalhador.viewModel.PaymentViewModel
import com.example.trabalhador.viewModel.SalaryBonusViewModel

class SalaryBonusFragment : Fragment() {

    private lateinit var binding: FragmentSalaryBonusBinding
    private lateinit var viewModel: SalaryBonusViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {

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
        val salary = binding.textInputEditTextSalary.text.toString().toFloat()
        val monthWork = binding.textInputEditTextDayWork.text.toString().toInt()
        val dependentsText = binding.textInputEditTextDependent.text.toString().toInt()
        val extra = binding.textInputEditTextExtraHour.text.toString().toFloat()

    }

    fun updateValues(salaryResult: SalaryResult){

    }
}