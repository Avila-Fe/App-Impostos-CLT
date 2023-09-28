package com.example.trabalhador.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.trabalhador.R
import com.example.trabalhador.databinding.FragmentPaymentBinding
import com.example.trabalhador.model.PaymentResult
import com.example.trabalhador.viewModel.PaymentViewModel

class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding
    private lateinit var viewModel: PaymentViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPaymentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[PaymentViewModel::class.java]

        viewModel.paymentResult.observe(viewLifecycleOwner, Observer { result ->
            updateValues(result)
        })

        binding.buttonCalculatePayment.setOnClickListener {
            calculatePayment()
        }


        return binding.root
    }

    private fun calculatePayment() {
        val salaryText = binding.textInputEditTextSalary.text.toString()
        val workDaysText = binding.textInputEditTextDay.text.toString()
        val earningsText = binding.textInputEditTextEarnings.text.toString()
        val discountText = binding.textInputEditTextDiscount.text.toString()
        val dependentsText = binding.textInputEditTextDependents.text.toString()

        if (validOk()) {
            val salary = salaryText.toFloat()
            val workDays = workDaysText.toInt()
            val earnings = earningsText.toFloat()
            val discount = discountText.toFloat()
            val dependents = dependentsText.toInt()

            viewModel.calculatePayment(salary, workDays, earnings, discount, dependents)

        } else {
            Toast.makeText(context, R.string.validation_fields, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateValues(paymentResult: PaymentResult) {

        binding.textVFgts.text = "R$ ${"%.2f".format(paymentResult.fgts)}"
        binding.textVInss.text = "R$ ${"%.2f".format(paymentResult.inss)}"
        binding.textVIrpf.text = "R$ ${"%.2f".format(paymentResult.irpf)}"
        binding.textVBase.text = "R$ ${"%.2f".format(paymentResult.base)}"
        binding.textTotalSalary.text = "R$ ${"%.2f".format(paymentResult.totalSalary)}"

        handlerBalance(paymentResult.totalSalary)
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
                    binding.textInputEditTextDay.text.toString() != "" &&
                    binding.textInputEditTextDay.text.toString().toInt() <= 30 &&
                    binding.textInputEditTextDay.text.toString().toInt() >= 1 &&
                    binding.textInputEditTextDependents.text.toString() != "" &&
                    binding.textInputEditTextDependents.text.toString().toInt() >= 0 &&
                    binding.textInputEditTextEarnings.text.toString() != "" &&
                    binding.textInputEditTextDiscount.text.toString() != "")
}