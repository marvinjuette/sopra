package view.utils

import kotlin.math.roundToInt

/**
 * Helper object containing a collection of functions to help positioning items at the center of the current scene.
 */
object ViewPositioning {

	/**
	 * Calculates the x value for at which the component needs to be so that it's center is centered inside the scene
	 *
	 * @param sceneWidth [Double] value that is the width of the entire scene
	 * @param componentWidth [Double] value that is the width of the component that is to be centered
	 * @return Value for the x-position so that the component is centered
	 */
	fun calculateCenterX(sceneWidth: Double, componentWidth: Double): Int {
		return ((sceneWidth - componentWidth) / 2).roundToInt()
	}

	/**
	 * Calculates the y value for at which the component needs to be so that it's center is centered inside the scene
	 *
	 * @param sceneHeight [Double] value that is the height of the entire scene
	 * @param componentHeight [Double] value that is the height of the component that is to be centered
	 * @return Value for the y-position so that the component is centered
	 */
	fun calculateCenterY(sceneHeight: Double, componentHeight: Double): Int {
		return ((sceneHeight - componentHeight) / 2).roundToInt()
	}
}