package extension

import extensions.onButtonClicked
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.mockito.kotlin.*
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.event.Event
import tools.aqua.bgw.event.KeyCode
import tools.aqua.bgw.event.KeyEvent

/**
 * This class contains all unit tests regarding the [Button]Extension onButtonClicked
 */
class ButtonExtensionTest {

	/**
	 * Test if on button clicked sets both on mouse clicked and on key pressed.
	 */
	@Test
	fun `test if on button clicked sets both on mouse clicked and on key pressed`() {
		val button = spy(Button())

		button.onButtonClicked { println("Test") }

		verify(button, times(1)).onKeyPressed = any()
		verify(button, times(1)).onMouseClicked = any()
	}

	/**
	 * Test if on key pressed works with space.
	 */
	@Test
	fun `test if on key pressed works with space`() {
		val button = Button()
		val action: (Event) -> Unit = mock()
		val keyEvent = KeyEvent(
			keyCode = KeyCode.SPACE,
			character = " ",
			isControlDown = false,
			isShiftDown = false,
			isAltDown = false
		)
		button.onButtonClicked(action)

		button.onKeyPressed!!.invoke(keyEvent)

		verify(action, times(1)).invoke(keyEvent)
	}

	/**
	 * Test if on key pressed works with enter.
	 */
	@Test
	fun `test if on key pressed works with enter`() {
		val button = Button()
		val action: (Event) -> Unit = mock()
		val keyEvent = KeyEvent(
			keyCode = KeyCode.ENTER,
			character = " ",
			isControlDown = false,
			isShiftDown = false,
			isAltDown = false
		)
		button.onButtonClicked(action)

		button.onKeyPressed!!.invoke(keyEvent)

		verify(action, times(1)).invoke(keyEvent)
	}

	/**
	 * Test if on key pressed works with no other key.
	 */
	@ParameterizedTest(name = "test if on key pressed should do nothing when {0} pressed")
	@EnumSource(KeyCode::class, names = ["ENTER", "SPACE"], mode = EnumSource.Mode.EXCLUDE)
	fun `test if on key pressed works with no other key`(keyCode: KeyCode) {
		val button = Button()
		val action: (Event) -> Unit = mock()
		val keyEvent = KeyEvent(
			keyCode = keyCode,
			character = " ",
			isControlDown = false,
			isShiftDown = false,
			isAltDown = false
		)
		button.onButtonClicked(action)

		button.onKeyPressed!!.invoke(keyEvent)

		verify(action, never()).invoke(keyEvent)
	}
}