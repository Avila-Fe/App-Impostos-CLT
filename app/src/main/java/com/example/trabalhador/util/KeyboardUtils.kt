package com.example.trabalhador.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

object KeyboardUtils {
    fun hideKeyboard(fragment: Fragment) {
        val imm = fragment.requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        var currentFocus = fragment.activity?.currentFocus

        if (currentFocus == null) {
            currentFocus = View(fragment.activity)
        }

        imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }

}