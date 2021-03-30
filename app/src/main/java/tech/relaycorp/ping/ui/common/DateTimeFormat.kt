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

    fun format(dateTime: ZonedDateTime?) =
        dateTime?.let {
            val date = Date.from(dateTime.toInstant())
            when {
                dateTime.isToday() -> timeFormatter.format(date)
                else -> dateTimeFormatter.format(date)
            }
        } ?: ""

    private fun ZonedDateTime.isToday() = toLocalDate() == LocalDate.now()
}
