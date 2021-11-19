package com.piconemarc.model

import java.util.*

fun getCalendarDate(date : Date?) : Calendar {
    val calendar =  Calendar.getInstance()
    if (date != null)
        calendar.time = date
    return calendar
}