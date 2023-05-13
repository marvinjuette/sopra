package view.controller

import service.RootService
import models.Constants
import view.Refreshable
import view.scenes.PlayerChangedScene

class PlayerChangedViewController(
	private val playerChangedScene: PlayerChangedScene,
	private val rootService: RootService
): Refreshable {

	init {
		playerChangedScene.apply { onKeyTyped = {
			rootService.gameService.onAllRefreshables { refreshAfterChangedCentralCards() }
		} }

		playerChangedScene.label.apply { onKeyTyped = {
			rootService.gameService.onAllRefreshables { refreshAfterPlayerRevealedCards() }
		} }

		playerChangedScene.label.apply { onMouseClicked = {
			rootService.gameService.onAllRefreshables { refreshAfterPlayerRevealedCards() }
		} }

	}

	override fun refreshAfterGameStart() {
		playerChangedScene.label.text = generateRevealHintText()
	}

	override fun refreshAfterPlayerChange() {
		playerChangedScene.label.text = generateRevealHintText()
	}

	private fun generateRevealHintText(): String {
		val playerName = rootService.gameState.players[rootService.gameState.currentPlayer].name
		return "${playerName}, ${Constants.PRESS_KEY_TO_SHOW_CARDS_TEXT}"
	}
}