package com.example.trabalhador.util

import android.content.Context
import android.widget.TextView
import com.example.trabalhador.R

object handlerUtils {

    fun handleBalance(textView: TextView, value: Float) {
        if (value <= 0) {
            textView.setTextColor(textView.resources.getColor(R.color.red))
        } else if (value > 0) {
            textView.setTextColor(textView.resources.getColor(R.color.green))
        }
    }

}