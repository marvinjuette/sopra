package view.animations

import tools.aqua.bgw.animation.MovementAnimation
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.core.BoardGameScene

/**
 * This is a helper object to provide a static moveAnimation
 */
object SelectCardAnimation {

	/**
	 * This function move a card up or down a bit.
	 *
	 * @param cardView References the [CardView] that is moved up or down
	 * @param moveDown [Boolean] to determine whether the card should move up or down
	 * @param scene Instance of [BoardGameScene] on which the animation should be played
	 */
	fun moveAnimation(cardView: CardView, moveDown: Boolean, scene: BoardGameScene) {
		val yChange = 50 * if (moveDown) 1 else -1
		scene.playAnimation(
			MovementAnimation(
				componentView = cardView,
				byY = yChange,
				duration = 500
			).apply {
				onFinished = {
					cardView.posY += yChange
				}
			}
		)
	}
}