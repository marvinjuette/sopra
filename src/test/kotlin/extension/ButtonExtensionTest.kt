package extension

import extensions.onButtonClicked
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.spy
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import tools.aqua.bgw.components.uicomponents.Button

/**
 * This class contains all unit tests regarding the [Button]Extension onButtonClicked
 */
class ButtonExtensionTest {

	/**
	 * Test if on button clicked sets both on mouse clicked and on key typed.
	 */
	@Test
	fun `test if on button clicked sets both on mouse clicked and on key typed`() {
		val button = spy(Button())

		button.onButtonClicked { println("Test") }

		verify(button, times(1)).onKeyTyped = any()
		verify(button, times(1)).onMouseClicked = any()
	}
}