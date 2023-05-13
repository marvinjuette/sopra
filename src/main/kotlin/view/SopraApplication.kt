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

class SopraApplication : BoardGameApplication(WINDOW_TITLE), Refreshable {

	private val rootService = RootService()

	private val startScene = StartScene()
	private val gameScene = GameScene()
	private val playerSwitchMenuScene = PlayerChangedScene()
	private val endScene = EndScene()

	private val startViewController = StartViewController(startScene, rootService)
	private val gameViewController = GameViewController(gameScene, rootService, this)
	private val playerChangedViewController = PlayerChangedViewController(playerSwitchMenuScene, rootService)
	private val endViewController = EndViewController(endScene, rootService)

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

	override fun refreshAfterGameStart() {
		this.hideMenuScene()
		this.showGameScene(gameScene)
		this.showMenuScene(playerSwitchMenuScene)
	}

	override fun refreshAfterPlayerChange() {
		this.showMenuScene(playerSwitchMenuScene)
	}

	override fun refreshAfterPlayerRevealedCards() {
		this.hideMenuScene()
	}

	override fun refreshAfterGameEnd() {
		this.showMenuScene(endScene)
	}

	override fun refreshAfterNewGame() {
		this.hideMenuScene()
		this.showMenuScene(startScene)
	}
}

