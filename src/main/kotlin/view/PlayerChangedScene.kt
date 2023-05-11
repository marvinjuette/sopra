package view

import service.RootService
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import utils.Colors.WHITE
import utils.Colors.WINDOW_BACKGROUND_COLOR
import utils.Constants.DEFAULT_WINDOW_HEIGHT
import utils.Constants.DEFAULT_WINDOW_WIDTH
import utils.Constants.PRESS_KEY_TO_SHOW_CARDS_TEXT

class PlayerChangedScene(private val rootService: RootService): MenuScene(
	width = DEFAULT_WINDOW_WIDTH,
	height = DEFAULT_WINDOW_HEIGHT,
	background = WINDOW_BACKGROUND_COLOR
), Refreshable {

	private val label = Label(
		posX = width / 4,
		posY = height / 4,
		width = width / 2,
		height = height / 2,
		font = Font(size = 24, color = WHITE.color, fontWeight = Font.FontWeight.BOLD),
	)

	override fun refreshAfterGameStart() {
		label.text = generateRevealHintText()
	}

	override fun refreshAfterPlayerChange() {
		label.text = generateRevealHintText()
	}

	init {
		label.apply { onKeyTyped = {
			rootService.gameService.onAllRefreshables { refreshAfterPlayerRevealedCards() }
		} }

		label.apply { onMouseClicked = {
			rootService.gameService.onAllRefreshables { refreshAfterPlayerRevealedCards() }
		} }

		addComponents(label)
	}

	private fun generateRevealHintText(): String {
		val playerName = rootService.gameState.players[rootService.gameState.currentPlayer].name
		return "${playerName}, $PRESS_KEY_TO_SHOW_CARDS_TEXT"
	}
}