package view.animations

import tools.aqua.bgw.animation.MovementAnimation
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.core.BoardGameScene

object SelectCardAnimation {

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