package com.example.pokemon.utils

import java.util.*

object DayUtil {
    @Throws(Exception::class)
    fun toDay(day: Int): String {
        when (day) {
            Calendar.SUNDAY -> return "Sunday"
            Calendar.MONDAY -> return "Monday"
            Calendar.TUESDAY -> return "Tuesday"
            Calendar.WEDNESDAY -> return "Wednesday"
            Calendar.THURSDAY -> return "Thursday"
            Calendar.FRIDAY -> return "Friday"
            Calendar.SATURDAY -> return "Saturday"
        }
        throw Exception("Could not locate day")
    }

    fun getDay(hour: Int, minute: Int): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar[Calendar.HOUR_OF_DAY] = hour
        calendar[Calendar.MINUTE] = minute
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        return if (calendar.timeInMillis <= System.currentTimeMillis()) {
            "Tomorrow"
        } else {
            "Today"
        }
    }
}