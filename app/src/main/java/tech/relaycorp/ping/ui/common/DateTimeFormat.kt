package tech.relaycorp.ping.ui.common

import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object DateTimeFormat {

    private val timeFormatter by lazy {
        DateTimeFormatter.ofPattern("HH:mm:ss")
    }
    private val dateTimeFormatter by lazy {
        DateTimeFormatter.ofPattern("MMM dd, HH:mm:ss")
    }

    private val dateTimeWithYearFormatter by lazy {
        DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss")
    }

    fun format(dateTime: ZonedDateTime) =
        when {
            dateTime.isToday() -> timeFormatter.format(dateTime)
            dateTime.isThisYear() -> dateTimeFormatter.format(dateTime)
            else -> dateTimeWithYearFormatter.format(dateTime)
        }

    private fun ZonedDateTime.isToday() = toLocalDate() == LocalDate.now()
    private fun ZonedDateTime.isThisYear() = year == LocalDate.now().year
}
