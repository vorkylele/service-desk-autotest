package ru.servicedesk.autotest.utils

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

/**
 * Независимый эталон расчёта контрольного срока для сверки с приложением.
 * Реализован иначе, чем в приложении: арифметикой по рабочим дням, а не обходом рабочих интервалов, —
 * чтобы ошибка алгоритма не повторилась в проверке. Рабочее время: понедельник — пятница, 09:00–18:00.
 */
object SlaOracle {
    private val WORK_START: LocalTime = LocalTime.of(9, 0)
    private val WORK_END: LocalTime = LocalTime.of(18, 0)
    private val WORK_DAY_MINUTES = ChronoUnit.MINUTES.between(WORK_START, WORK_END)

    fun dueAt(createdAt: LocalDateTime, normMinutes: Int): LocalDateTime {
        val start = firstWorkingMoment(createdAt.truncatedTo(ChronoUnit.MINUTES))
        val minutesFromDayStart = ChronoUnit.MINUTES.between(start.toLocalDate().atTime(WORK_START), start) + normMinutes
        val fullDays = (minutesFromDayStart - 1) / WORK_DAY_MINUTES
        val minutes = minutesFromDayStart - fullDays * WORK_DAY_MINUTES
        return addWorkingDays(start.toLocalDate(), fullDays).atTime(WORK_START).plusMinutes(minutes)
    }

    private fun firstWorkingMoment(moment: LocalDateTime): LocalDateTime = when {
        !isWorkingDay(moment.toLocalDate()) || moment.toLocalTime() >= WORK_END ->
            nextWorkingDay(moment.toLocalDate()).atTime(WORK_START)
        moment.toLocalTime() < WORK_START -> moment.toLocalDate().atTime(WORK_START)
        else -> moment
    }

    private fun addWorkingDays(date: LocalDate, days: Long): LocalDate =
        (1..days).fold(date) { current, _ -> nextWorkingDay(current) }

    private fun nextWorkingDay(date: LocalDate): LocalDate =
        generateSequence(date.plusDays(1)) { it.plusDays(1) }.first(::isWorkingDay)

    private fun isWorkingDay(date: LocalDate): Boolean =
        date.dayOfWeek != DayOfWeek.SATURDAY && date.dayOfWeek != DayOfWeek.SUNDAY
}
