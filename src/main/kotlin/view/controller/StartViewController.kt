package view.controller

import extensions.onButtonClicked
import service.RootService
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.util.Font
import models.Constants
import models.Constants.DEFAULT_MARGIN
import models.Constants.DEFAULT_SPACING
import view.Refreshable
import view.scenes.StartScene
import java.awt.Color
import kotlin.system.exitProcess

class StartViewController(
	private val startScene: StartScene,
	private val rootService: RootService
): Refreshable {

	init {
		startScene.startButton.onButtonClicked {
			if (startScene.playerName1.text.isBlank() || startScene.playerName2.text.isBlank()) {
				showErrorLabel()
			} else {
				rootService.gameService.startNewGame(getPlayerNamesAsList())
			}
		}

		startScene.quitButton.onButtonClicked { exitProcess(0) }
	}

	private fun showErrorLabel() {
		startScene.errorLabel.posX = .0
		startScene.errorLabel.posY = startScene.backgroundButton.posY + startScene.contentHeight
					+ DEFAULT_MARGIN + DEFAULT_SPACING
		startScene.errorLabel.width = startScene.getSceneWidth()
		startScene.errorLabel.text = Constants.AT_LEAST_TWO_PLAYERS_ARE_REQUIRED_ERROR_MESSAGE
		startScene.errorLabel.font = Font(size = 24, color = Color.RED)
		startScene.errorLabel.alignment = Alignment.BOTTOM_CENTER
	}

	private fun getPlayerNamesAsList(): List<String> {
		val playerNames = mutableListOf<String>()
		playerNames.add(startScene.playerName1.text.trim())
		playerNames.add(startScene.playerName2.text.trim())

		if (startScene.playerName3.text.isNotBlank()) {
			playerNames.add(startScene.playerName3.text.trim())
		}

		if (startScene.playerName4.text.isNotBlank()) {
			playerNames.add(startScene.playerName4.text.trim())
		}

		return playerNames.toList()
	}

}