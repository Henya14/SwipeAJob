package hu.bme.aut.android.swipeajob.Constants

import java.util.Calendar


public object Consts {
    const val YEAR_PICKER_MIN_VALUE = 1900
    const val YEAR_PICKER_MAX_VALUE = 2100
    val YEAR_PICKER_INITIAL_VALUE: Int = Calendar.getInstance().get(Calendar.YEAR)

}