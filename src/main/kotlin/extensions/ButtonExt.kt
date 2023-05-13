package extensions

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.event.Event

/**
 * Extension function for [Button]s so that the same action is triggert on keyTyped and mouseClickedEvent
 */
fun Button.onButtonClicked(action: (Event) -> Unit) {
	this.apply { onMouseClicked = action }
	this.apply { onKeyTyped = action }
}