package com.example.persecdemo.utils

import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker

fun showDatePicker(fragmentManager: FragmentManager,
                   onDateClicked: (String) -> Unit) {
    val datePicker = MaterialDatePicker.Builder.datePicker()
        .setTitleText("Select date")
        .build()

    datePicker.addOnPositiveButtonClickListener { selection ->
        val dateString = formatDate(selection)
        println("Selected API Date: $dateString")
        onDateClicked(dateString)
    }

    datePicker.show(fragmentManager, "DATE_PICKER")
}