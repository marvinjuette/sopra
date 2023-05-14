package view.controller

import extensions.onButtonClicked
import service.RootService
import view.Refreshable
import view.scenes.StartScene
import kotlin.system.exitProcess

/**
 * This is the controller class for the end scene
 *
 * @param startScene References the [StartScene] that is to be controled by this controller
 * @param rootService References the [RootService] to access things like the [entity.GameState]
 */
class StartViewController(
	private val startScene: StartScene,
	private val rootService: RootService
): Refreshable {

	/**
	 * Initialize some scene elements with their base functionality
	 */
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

	/**
	 * Shows the error with the error message that at least two player names need to be provided.
	 */
	internal fun showErrorLabel() {
		startScene.errorLabel.isVisible = true
	}

	/**
	 * Reads all entered player names and add them to a list.
	 *
	 * @return playerNames A [List<String>] that contains all 2-4 entered player names.
	 */
	internal fun getPlayerNamesAsList(): List<String> {
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