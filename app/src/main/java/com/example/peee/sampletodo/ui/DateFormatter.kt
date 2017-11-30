package com.example.peee.sampletodo.ui

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {
    private const val PATTERN = "yyyy/MM/dd HH:mm"
    private val format = SimpleDateFormat(PATTERN, Locale.US)

    fun toString(time: Long): String = format.format(Date(time))

    fun toMillis(dateFormat: String): Long {
        try {
            return format.parse(dateFormat).time
        } catch (e: ParseException) {
            e.printStackTrace()
            return Date(0).time
        }
    }
}