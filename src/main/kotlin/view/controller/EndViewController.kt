package view.controller

import entity.Player
import extensions.onButtonClicked
import service.RootService
import view.Refreshable
import view.scenes.EndScene
import kotlin.system.exitProcess

class EndViewController(
	private val endScene: EndScene,
	private val rootService: RootService
): Refreshable {

	init {
		endScene.newGameButton.onButtonClicked { rootService.gameService.onAllRefreshables { refreshAfterNewGame() } }
		endScene.quitButton.onButtonClicked { exitProcess(0) }
		endScene.newGameWithSamePlayerButton.onButtonClicked { startNewGameWithSamePlayers() }
	}

	fun startNewGameWithSamePlayers() {
		val playerNames = rootService.gameState.players.map(Player::name)
		rootService.gameService.startNewGame(playerNames)
	}

	override fun refreshAfterGameEnd() {
		val playerAmount = rootService.gameState.players.size

		val player1 = rootService.gameState.players[0]
		endScene.player1NameLabel.text = player1.name
		endScene.player1Score.text = rootService.gameService.calculateScore(player1.handCards).toString()

		val player2 = rootService.gameState.players[1]
		endScene.player2NameLabel.text = player2.name
		endScene.player2Score.text = rootService.gameService.calculateScore(player2.handCards).toString()

		if (playerAmount == 3) {
			val player3 = rootService.gameState.players[2]
			endScene.player3NameLabel.isVisible = true
			endScene.player3NameLabel.text = player3.name
			endScene.player3Score.isVisible = true
			endScene.player3Score.text = rootService.gameService.calculateScore(player3.handCards).toString()
		}

		if (playerAmount == 4) {
			val player4 = rootService.gameState.players[3]
			endScene.player4NameLabel.isVisible = true
			endScene.player4NameLabel.text = player4.name
			endScene.player4Score.isVisible = true
			endScene.player4Score.text = rootService.gameService.calculateScore(player4.handCards).toString()
		}
	}
}