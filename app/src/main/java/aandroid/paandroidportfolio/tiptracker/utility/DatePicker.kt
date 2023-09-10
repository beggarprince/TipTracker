package aandroid.paandroidportfolio.tiptracker.utility

import android.app.DatePickerDialog
import android.content.Context
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DatePicker {
    fun showDatePickerDialog(
        context: Context?,
        titleOverride: String? = null,
        onDateSelected: (String) -> Unit
    ) {
        val calendar = Calendar.getInstance()
        context?.let {
            val datePickerDialog = DatePickerDialog(
                it,
                { _, year, month, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    onDateSelected(
                        SimpleDateFormat(
                            "yyyy-MM-dd",
                            Locale.US
                        ).format(calendar.time)
                    )
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            val title = titleOverride ?: "Select Date"
            datePickerDialog.setTitle(title)
            datePickerDialog.show()
        }

    }
}