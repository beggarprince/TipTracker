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