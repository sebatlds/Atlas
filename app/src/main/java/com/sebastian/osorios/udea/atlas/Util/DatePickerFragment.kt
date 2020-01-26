package com.sebastian.osorios.udea.atlas.Util

import java.util.*
import java.text.ParseException
import java.text.SimpleDateFormat


class DatePickerFragment {


    fun convertDateToMillis(givenDateString: String): Long {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        var timeInMilliseconds = System.currentTimeMillis() - 1000
        try {
            val mDate: Date = sdf.parse(givenDateString)
            timeInMilliseconds = mDate.getTime()
            println("Date in milliseconds: $timeInMilliseconds")
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return timeInMilliseconds
    }
}