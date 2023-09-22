package aandroid.paandroidportfolio.tiptracker.utility

import android.content.Context

class UserPreferences private constructor(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences("cardata", Context.MODE_PRIVATE)

    companion object {
        const val KEY_FLOAT_MPG = "floatMPG"

        @Volatile
        private var INSTANCE: UserPreferences? = null

        fun getInstance(context: Context): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserPreferences(context).also { INSTANCE = it }
            }
        }
    }

    // Getter and Setter for floatMPG.
    var floatMPG: Float
        get() = sharedPreferences.getFloat(KEY_FLOAT_MPG, 25f)
        set(value) {
            //should move the logic checks here and out of the fragment
            sharedPreferences.edit().putFloat(KEY_FLOAT_MPG, value).apply()
        }

}