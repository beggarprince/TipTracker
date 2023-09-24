package aandroid.paandroidportfolio.tiptracker.utility
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Calendar.getCurrentMonday(): String {
    firstDayOfWeek = Calendar.MONDAY
    set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
    return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(time)
}

fun Calendar.getCurrentSunday(): String {
    firstDayOfWeek = Calendar.MONDAY
    set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
    return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(time)
}

fun Calendar.getMonthStart(): String {
    set(Calendar.DAY_OF_MONTH, 1)
    return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(time)
}

fun Calendar.getMonthEnd(): String {
    set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
    return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(time)
}