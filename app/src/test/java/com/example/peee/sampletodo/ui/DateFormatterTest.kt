package com.example.peee.sampletodo.ui

import org.junit.Assert.*
import org.junit.Test
import java.util.*

class DateFormatterTest {
    private val time: Long

    init {
        val calendar = Calendar.getInstance()
        calendar.apply {
            set(Calendar.YEAR, 2017)
            set(Calendar.MONTH, Calendar.OCTOBER)
            set(Calendar.DAY_OF_MONTH, 15)
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 30)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        time = calendar.timeInMillis
    }

    @Test
    fun millisToString() {
        assertEquals("2017/10/15 09:30", DateFormatter.toString(time))
    }

    @Test
    fun millisToDateString() {
        assertEquals("2017/10/15", DateFormatter.toDateString(time))
    }

    @Test
    fun millisToTimeString() {
        assertEquals("09:30", DateFormatter.toTimeString(time))
    }

    @Test
    fun stringToMillis() {
        assertEquals(time, DateFormatter.toMillis("2017/10/15 09:30"))
    }

    @Test
    fun wrongStringToMillis() {
        assertEquals(0, DateFormatter.toMillis("2017-10-15 09:30"))
    }
}