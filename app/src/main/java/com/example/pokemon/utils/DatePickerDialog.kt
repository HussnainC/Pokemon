package com.example.pokemon.utils

import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*
import javax.inject.Inject


class DatePickerDialog @Inject constructor(val now: Calendar) {
    fun getRageDatePicker(
        title: String = "Select Date",
        positiveButtonText: String = "Ok",
        negativeButtonText: String = "Cancel"
    ): MaterialDatePicker<androidx.core.util.Pair<Long, Long>> {
        val datePicker = MaterialDatePicker.Builder.dateRangePicker()
        datePicker.apply {
            setTitleText(title)
            setPositiveButtonText(positiveButtonText)
            setNegativeButtonText(negativeButtonText)
            setSelection(androidx.core.util.Pair(now.timeInMillis, now.timeInMillis))
        }
        return datePicker.build()
    }

    fun getSimpleDatePicker(
        title: String = "Select Date",
        positiveButtonText: String = "Ok",
        negativeButtonText: String = "Cancel"
    ): MaterialDatePicker<Long> {
        val datePicker = MaterialDatePicker.Builder.datePicker()
        datePicker.apply {
            setTitleText(title)
            setPositiveButtonText(positiveButtonText)
            setNegativeButtonText(negativeButtonText)
            setSelection(now.timeInMillis)
        }
        return datePicker.build()
    }

}