package com.example.trabalhador.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.trabalhador.R
import com.example.trabalhador.databinding.FragmentPaymentBinding
import com.example.trabalhador.model.PaymentResult
import com.example.trabalhador.util.KeyboardUtils
import com.example.trabalhador.util.handlerUtils
import com.example.trabalhador.viewModel.PaymentViewModel

class PaymentFragment() : Fragment(R.layout.fragment_payment) {

    private lateinit var binding: FragmentPaymentBinding
    private lateinit var viewModel: PaymentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View {
        viewModel = ViewModelProvider(this)[PaymentViewModel::class.java]
        binding = FragmentPaymentBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObservables()

        with(binding) {
            buttonCalculatePayment.setOnClickListener {
                calculatePayment()
                KeyboardUtils.hideKeyboard(this@PaymentFragment)
            }
        }
    }

    private fun initObservables() {
        viewModel.paymentResult.observe(viewLifecycleOwner) { result -> updateValues(result) }
    }

    private fun calculatePayment() {
        if (validOk()) {
            val salary = binding.textInputEditTextSalary.text.toString().toFloat()
            val workDays = binding.textInputEditTextDay.text.toString().toInt()
            val earnings = binding.textInputEditTextEarnings.text.toString().toFloat()
            val discount = binding.textInputEditTextDiscount.text.toString().toFloat()
            val dependents = binding.textInputEditTextDependents.text.toString().toInt()

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

        handlerUtils.handleBalance(binding.textTotalSalary, paymentResult.totalSalary)
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
