package view

import service.RootService
import tools.aqua.bgw.core.BoardGameApplication
import models.Constants.WINDOW_TITLE
import view.controller.EndViewController
import view.controller.GameViewController
import view.controller.PlayerChangedViewController
import view.controller.StartViewController
import view.scenes.EndScene
import view.scenes.GameScene
import view.scenes.PlayerChangedScene
import view.scenes.StartScene

/**
 * This class ia an implementation of the [BoardGameApplication] and is needed in order for the game to be run in BGW
 */
class SopraApplication: BoardGameApplication(WINDOW_TITLE), Refreshable {

	private val rootService = RootService()

	private val startScene = StartScene()
	private val gameScene = GameScene()
	private val playerChangedScene = PlayerChangedScene()
	private val endScene = EndScene()

	private val startViewController = StartViewController(startScene, rootService)
	private val gameViewController = GameViewController(gameScene, rootService, this)
	private val playerChangedViewController = PlayerChangedViewController(playerChangedScene, rootService)
	private val endViewController = EndViewController(endScene, rootService)

	/**
	 * Initialize the whole game and add all refreshables to the services.
	 */
   init {
		rootService.addRefreshables(
			this,
			startViewController,
			gameViewController,
			playerChangedViewController,
			endViewController,
		)
		this.showMenuScene(startScene)
	}

	/**
	 * Hides the [StartScene] and displays the [GameScene] with the [PlayerChangedScene] so that the other players
	 * cannot see the hand cards of the first player immediately after pressing the start button.
	 */
	override fun refreshAfterGameStart() {
		this.hideMenuScene()
		this.showGameScene(gameScene)
		this.showMenuScene(playerChangedScene)
	}

	/**
	 * Displays the [PlayerChangedScene] to prevent the previous player to view the cards of the current player
	 * while switching seats.
	 */
	override fun refreshAfterPlayerChange() {
		this.showMenuScene(playerChangedScene)
	}

	/**
	 * Hides the [PlayerChangedScene] so that the current player can see his cards.
	 */
	override fun refreshAfterPlayerRevealedCards() {
		this.hideMenuScene()
	}

	/**
	 * Displays the [EndScene] when the game is over so that the player can see their scores.
	 */
	override fun refreshAfterGameEnd() {
		this.showMenuScene(endScene)
	}

	/**
	 * Shows the [StartScene] so that the player names can be altered or a new player can be added
	 * or a player can be removed.
	 */
	override fun refreshAfterNewGame() {
		this.hideMenuScene()
		this.showMenuScene(startScene)
	}
}

