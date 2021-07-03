package de.westwing.campaignbrowser.common

import android.content.res.Resources
import android.util.TypedValue

class DensityConverter {
    companion object {
        fun toPixel(resources: Resources, dp: Int) : Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(),
                resources.displayMetrics
            ).toInt()
        }
    }
}