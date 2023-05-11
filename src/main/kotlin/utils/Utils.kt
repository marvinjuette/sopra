package utils

import kotlin.math.roundToInt

object Utils {

    // Sadly this is necessary because the front-end framework decided that nobody wants to center stuff
    fun calculateCenterX(sceneWidth: Double, componentWidth: Double): Int {
        return ((sceneWidth - componentWidth) / 2).roundToInt()
    }

    fun calculateCenterY(sceneHeight: Double, componentHeight: Double): Int {
        return ((sceneHeight - componentHeight) / 2).roundToInt()
    }
}