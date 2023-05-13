package utils

import kotlin.math.roundToInt

object ViewPositioning {

	fun calculateCenterX(sceneWidth: Double, componentWidth: Double): Int {
		return ((sceneWidth - componentWidth) / 2).roundToInt()
	}

	fun calculateCenterY(sceneHeight: Double, componentHeight: Double): Int {
		return ((sceneHeight - componentHeight) / 2).roundToInt()
	}
}