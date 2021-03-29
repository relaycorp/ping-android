package tech.relaycorp.ping.ui.common

import java.text.DateFormat
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.*

object DateTimeFormat {

    private val timeFormatter by lazy {
        DateFormat.getTimeInstance(DateFormat.MEDIUM)
    }
    private val dateTimeFormatter by lazy {
        DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM)
    }

    fun format(dateTime: ZonedDateTime) = when {
        dateTime.isToday() -> timeFormatter.format(Date.from(dateTime.toInstant()))
        else -> dateTimeFormatter.format(Date.from(dateTime.toInstant()))
    }

    private fun ZonedDateTime.isToday() = toLocalDate() == LocalDate.now()
}
