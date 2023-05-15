package extensions

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.event.Event
import tools.aqua.bgw.event.KeyCode

/**
 * Extension function for [Button]s so that the same action is triggert on keyTyped and mouseClickedEvent
 */
fun Button.onButtonClicked(action: (Event) -> Unit) {
	this.apply { onMouseClicked = action }
	this.apply { onKeyPressed = {
		if (it.keyCode == KeyCode.SPACE || it.keyCode == KeyCode.ENTER) {
			it.run(action)
		}
	} }
}