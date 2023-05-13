package view.scenes

import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import models.Colors.WHITE
import models.Colors.WINDOW_BACKGROUND_COLOR
import models.Constants.DEFAULT_WINDOW_HEIGHT
import models.Constants.DEFAULT_WINDOW_WIDTH
import view.Refreshable

/**
 * Player change scene which is used to blur the game scene so that other players cannot see the current players
 * hand cards after a player change. It also displays a hint on how to proceed for the user.
 *
 * @property label [Label] to display the hint for the user
 */
class PlayerChangedScene: MenuScene(
	width = DEFAULT_WINDOW_WIDTH,
	height = DEFAULT_WINDOW_HEIGHT,
	background = WINDOW_BACKGROUND_COLOR
), Refreshable {

	internal val label = Label(
		posX = 0,
		posY = 0,
		width = width,
		height = height,
		font = Font(size = 24, color = WHITE.color, fontWeight = Font.FontWeight.BOLD),
	)

	/**
	 * Initialize the player change scene
	 */
	init {
		addComponents(label)
	}
}