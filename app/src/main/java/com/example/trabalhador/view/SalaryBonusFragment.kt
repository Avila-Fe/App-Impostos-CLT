package com.example.trabalhador.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.trabalhador.R
import com.example.trabalhador.databinding.FragmentSalaryBonusBinding
import com.example.trabalhador.databinding.FragmentVacationBinding

class SalaryBonusFragment : Fragment() {
    private lateinit var binding: FragmentSalaryBonusBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {

        binding = FragmentSalaryBonusBinding.inflate(layoutInflater)

        binding.buttonCalculatePayment.setOnClickListener {
           // calculate()
        }

        return binding.root
    }


}