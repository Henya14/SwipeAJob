package hu.bme.aut.android.swipeajob.Globals

import java.util.Calendar


object Consts {
    const val YEAR_PICKER_MIN_VALUE = 1900
    const val YEAR_PICKER_MAX_VALUE = 2100
    val YEAR_PICKER_INITIAL_VALUE: Int = Calendar.getInstance().get(Calendar.YEAR)

    const val IMAGE_PICKER_MAX_WIDTH  = 300
    const val IMAGE_PICKER_MAX_HEIGHT = 400

}