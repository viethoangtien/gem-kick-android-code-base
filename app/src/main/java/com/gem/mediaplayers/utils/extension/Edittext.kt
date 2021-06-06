package com.gem.mediaplayers.utils.extension

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.validateInputWriting(func: (Boolean) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            func.invoke(!p0.toString().isNullOrEmpty())
        }

        override fun afterTextChanged(p0: Editable?) {

        }

    })
}
