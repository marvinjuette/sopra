package extensions

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.event.Event

fun Button.onButtonClicked(action: (Event) -> Unit) {
	this.apply { onMouseClicked = action }
	this.apply { onKeyTyped = action }
}