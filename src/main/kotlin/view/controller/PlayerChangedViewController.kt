package view.controller

import service.RootService
import models.Constants
import view.Refreshable
import view.scenes.EndScene
import view.scenes.PlayerChangedScene

/**
 * This is the controller class for the end scene
 *
 * @param endScene References the [EndScene] that is to be controled by this controller
 * @param rootService References the [RootService] to access things like the [entity.GameState]
 */
class PlayerChangedViewController(
	private val playerChangedScene: PlayerChangedScene,
	private val rootService: RootService
): Refreshable {

	/**
	 * Initialize some scene elements with their base functionality
	 */
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

	/**
	 * Updates the text to display to include player name
	 */
	override fun refreshAfterGameStart() {
		playerChangedScene.label.text = generateRevealHintText()
	}

	/**
	 * Updates the text to display to include player name
	 */
	override fun refreshAfterPlayerChange() {
		playerChangedScene.label.text = generateRevealHintText()
	}

	/**
	 * This method generates the info text for the player with instructions on how to proceed
	 */
	private fun generateRevealHintText(): String {
		val playerName = rootService.gameState.players[rootService.gameState.currentPlayer].name
		return "${playerName}, ${Constants.PRESS_KEY_TO_SHOW_CARDS_TEXT}"
	}
}