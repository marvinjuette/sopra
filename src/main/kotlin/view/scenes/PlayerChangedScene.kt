package view.scenes

import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import models.Colors.WHITE
import models.Colors.WINDOW_BACKGROUND_COLOR
import models.Constants.DEFAULT_WINDOW_HEIGHT
import models.Constants.DEFAULT_WINDOW_WIDTH
import view.Refreshable

class PlayerChangedScene: MenuScene(
	width = DEFAULT_WINDOW_WIDTH,
	height = DEFAULT_WINDOW_HEIGHT,
	background = WINDOW_BACKGROUND_COLOR
), Refreshable {

	internal val label = Label(
		posX = width / 4,
		posY = height / 4,
		width = width / 2,
		height = height / 2,
		font = Font(size = 24, color = WHITE.color, fontWeight = Font.FontWeight.BOLD),
	)

	init {
		addComponents(label)
	}
}