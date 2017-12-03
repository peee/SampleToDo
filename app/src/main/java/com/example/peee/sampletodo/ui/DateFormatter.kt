package com.example.peee.sampletodo.ui

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * A helper object to convert string to time in format of [PATTERN], or vice versa
 */
object DateFormatter {
    /** date format of "yyyy/MM/dd HH:mm" */
    private const val PATTERN = "yyyy/MM/dd HH:mm"
    private val format = SimpleDateFormat(PATTERN, Locale.US)

    /**
     * Formats time into [PATTERN].
     *
     * @param time time in millis
     * @return string formatted
     */
    fun toString(time: Long): String = format.format(Date(time))

    /**
     * Formats time into [PATTERN], only date part.
     *
     * @param time time in millis
     * @return string formatted
     */
    fun toDateString(time: Long): String = toString(time).split(" ")[0]

    /**
     * Formats time into [PATTERN], only time part.
     *
     * @param time time in millis
     * @return string formatted
     */
    fun toTimeString(time: Long): String = toString(time).split(" ")[1]

    /**
     * Parses formats of [PATTERN] into time millis.
     *
     * @param dateFormat string that represents time in format of [PATTERN]
     * @return time in millis parsed from the given string, or 0 on exception
     */
    fun toMillis(dateFormat: String): Long {
        try {
            return format.parse(dateFormat).time
        } catch (e: ParseException) {
            e.printStackTrace()
            return Date(0).time
        }
    }
}