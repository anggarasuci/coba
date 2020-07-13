package com.example.coba.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.NumberFormat
import java.text.ParseException
import java.util.*

fun EditText.afterTextChanged(action: (String) -> Unit) {
    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            action(editable.toString())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

    }

    addTextChangedListener(textWatcher)
}