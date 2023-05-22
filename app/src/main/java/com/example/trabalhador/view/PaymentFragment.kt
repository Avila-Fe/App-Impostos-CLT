package com.example.trabalhador.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.trabalhador.R
import com.example.trabalhador.R.id.button_calculatePayment
import com.example.trabalhador.databinding.FragmentPaymentBinding
import com.example.trabalhador.viewModel.PaymentViewModel

class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding
    private lateinit var viewModel: PaymentViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // val view = inflater.inflate(R.layout.fragment_payment, container, false)
        binding = FragmentPaymentBinding.inflate(layoutInflater)

        // var btCalculate: Button = view.findViewById(button_calculatePayment)
        binding.buttonCalculatePayment.setOnClickListener {
            calculate()
        }

        // binding.buttonCalculatePayment.setOnClickListener(this)

        return binding.root
    }

    private fun calculate() {

        if (validOk()) {
            val salary = binding.textInputEditTextSalary.text.toString().toFloat()
            val workDays = binding.textInputEditTextDay.text.toString().toInt()
            val earnings = binding.textInputEditTextEarnings.text.toString().toFloat()
            val discount = binding.textInputEditTextDiscount.text.toString().toFloat()
            val dependents = binding.textInputEditTextDependents.text.toString().toInt()


            val totalFgts = (((salary / 30) * workDays) + earnings) * 0.08f
            binding.textVFgts.text = "R$ ${"%.2f".format(totalFgts)}"

            val totalInss = calculateInss(salary, workDays, earnings)
            binding.textVInss.text = "R$ ${"%.2f".format(totalInss)}"

            val totalIrpf = calculateIrpf(salary, workDays, earnings, dependents)
            binding.textVIrpf.text = "R$ ${"%.2f".format(totalIrpf)}"

            val totalBase = (salary / 30) * workDays
            binding.textVBase.text = "R$ ${"%.2f".format(totalBase)}"

            val salaryLiquid = (totalBase - discount - totalInss - totalIrpf) + earnings
            binding.textTotalSalary.text = "R$ ${"%.2f".format(salaryLiquid)}"
        } else {
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
