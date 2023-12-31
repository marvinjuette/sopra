package view.scenes

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.*
import view.utils.ViewPositioning.calculateCenterX
import view.utils.ViewPositioning.calculateCenterY
import view.Refreshable
import view.models.Colors.CONTENT_BACKGROUND_COLOR
import view.models.Colors.CYAN
import view.models.Colors.RED
import view.models.Colors.WHITE
import view.models.Colors.WINDOW_BACKGROUND_COLOR
import view.models.Constants.DEFAULT_MARGIN
import view.models.Constants.DEFAULT_SPACING
import view.models.Constants.MENU_BUTTON_FONT
import view.models.Constants.NEW_GAME_BUTTON_TEXT
import view.models.Constants.NEW_GAME_WITH_SAME_PLAYERS_BUTTON_TEXT
import view.models.Constants.PLAYER_NAME_FONT
import view.models.Constants.QUIT_BUTTON_TEXT
import view.models.Constants.SCORE_FONT
import view.models.Constants.SCORE_TEXT
import view.models.Constants.TEXT_FIELD_HEIGHT
import view.models.Constants.TEXT_FIELD_WIDTH
import view.models.Constants.TITLE_LABEL_FONT

/**
 * End scene to display player names and their score.
 *
 * @property playerNameLabelWidth describes the width of a player name label
 * @property scoreLabelWidth describes the width of a score label
 * @property scoreLabelHeight describes the height of a score label
 * @property contentWidth describes the width of all content
 * @property contentHeight describes the height of all content
 * @property scoreLabel [Label] to display the score title
 * @property player1NameLabel [Label] to display the name of player 1
 * @property player1Score [Label] to display the score of player 1
 * @property player2NameLabel [Label] to display the name of player 2
 * @property player2Score [Label] to display the score of player 2
 * @property player3NameLabel [Label] to display the name of player 3
 * @property player3Score [Label] to display the score of player 3
 * @property player4NameLabel [Label] to display the name of player 4
 * @property player4Score [Label] to display the score of player 4
 * @property newGameButton [Button] to open the players dialog again
 * @property quitButton [Button] to quit the application
 * @property newGameWithSamePlayerButton [Button] to directly start a new game with the same players
 * @property backgroundButton [Button] Rick Astley
 */
class EndScene: MenuScene(
	width = DEFAULT_WINDOW_WIDTH,
	height = DEFAULT_WINDOW_HEIGHT,
	background = WINDOW_BACKGROUND_COLOR
), Refreshable {

	private val playerNameLabelWidth = TEXT_FIELD_WIDTH / 2 - 0.5 * DEFAULT_SPACING
	private val scoreLabelWidth = TEXT_FIELD_WIDTH / 2 - DEFAULT_SPACING
	private val scoreLabelHeight = TEXT_FIELD_HEIGHT / 2
	private val contentWidth = 2 * playerNameLabelWidth + DEFAULT_SPACING
	private val contentHeight = 6 * scoreLabelHeight + 3.5 * DEFAULT_SPACING + TEXT_FIELD_HEIGHT

	private val scoreLabel = Label(
		posX = calculateCenterX(width, contentWidth),
		posY = DEFAULT_MARGIN,
		width = contentWidth,
		height = TEXT_FIELD_HEIGHT,
		text = SCORE_TEXT,
		font = TITLE_LABEL_FONT,
	)

	internal val player1NameLabel = Label(
		posX = calculateCenterX(width, contentWidth),
		posY = scoreLabel.posY + scoreLabel.height + DEFAULT_SPACING,
		width = playerNameLabelWidth,
		height = scoreLabelHeight,
		text = "Spieler 1:",
		font = PLAYER_NAME_FONT,
	)

	internal val player1Score = Label(
		posX = player1NameLabel.posX + 0.25 * DEFAULT_SPACING,
		posY = player1NameLabel.posY + player1NameLabel.height,
		width = scoreLabelWidth,
		height = scoreLabelHeight,
		text = "Score 1",
		font = SCORE_FONT,
		visual = WHITE
	)

	internal val player2NameLabel = Label(
		posX = player1NameLabel.posX + player1NameLabel.width + DEFAULT_SPACING,
		posY = player1NameLabel.posY,
		width = playerNameLabelWidth,
		height = scoreLabelHeight,
		text = "Spieler 2:",
		font = PLAYER_NAME_FONT,
	)

	internal val player2Score = Label(
		posX = player2NameLabel.posX + 0.25 * DEFAULT_SPACING,
		posY = player1Score.posY,
		width = scoreLabelWidth,
		height = scoreLabelHeight,
		text = "Score 2",
		font = SCORE_FONT,
		visual = WHITE
	)

	internal val player3NameLabel = Label(
		posX = player1NameLabel.posX,
		posY = player1Score.posY + player1Score.height + DEFAULT_SPACING / 2,
		width = playerNameLabelWidth,
		height = scoreLabelHeight,
		text = "Spieler 3:",
		font = PLAYER_NAME_FONT,
	)

	internal val player3Score = Label(
		posX = player3NameLabel.posX + 0.25 * DEFAULT_SPACING,
		posY = player3NameLabel.posY + player3NameLabel.height,
		width = scoreLabelWidth,
		height = scoreLabelHeight,
		text = "Score 3",
		font = SCORE_FONT,
		visual = WHITE
	)

	internal val player4NameLabel = Label(
		posX = player2NameLabel.posX,
		posY = player3NameLabel.posY,
		width = playerNameLabelWidth,
		height = scoreLabelHeight,
		text = "Spieler 4:",
		font = PLAYER_NAME_FONT,
	)

	internal val player4Score = Label(
		posX = player4NameLabel.posX + 0.25 * DEFAULT_SPACING,
		posY = player3Score.posY,
		width = scoreLabelWidth,
		height = scoreLabelHeight,
		text = "Score 4",
		font = SCORE_FONT,
		visual = WHITE
	)

	internal val newGameButton = Button(
		posX = player3NameLabel.posX,
		posY = player3Score.posY + player3Score.height + DEFAULT_SPACING,
		width = playerNameLabelWidth,
		height = TEXT_FIELD_HEIGHT,
		text = NEW_GAME_BUTTON_TEXT,
		font = MENU_BUTTON_FONT,
		visual = CYAN
	)

	internal val quitButton = Button(
		posX = newGameButton.posX + newGameButton.width + DEFAULT_SPACING,
		posY = newGameButton.posY,
		width = playerNameLabelWidth,
		height = TEXT_FIELD_HEIGHT,
		text = QUIT_BUTTON_TEXT,
		font = MENU_BUTTON_FONT,
		visual = RED
	)

	internal val newGameWithSamePlayerButton = Button(
		posX = newGameButton.posX,
		posY = newGameButton.posY + newGameButton.height + DEFAULT_SPACING / 2,
		width = contentWidth,
		height = TEXT_FIELD_HEIGHT,
		text = NEW_GAME_WITH_SAME_PLAYERS_BUTTON_TEXT,
		font = MENU_BUTTON_FONT,
		visual = CYAN
	)

	private val backgroundButton = Button(
		posX = calculateCenterX(width, contentWidth) - 0.5 * DEFAULT_MARGIN,
		posY = calculateCenterY(height, contentHeight) - 0.5 * DEFAULT_MARGIN,
		width = contentWidth + DEFAULT_MARGIN,
		height = contentHeight + DEFAULT_MARGIN,
		visual = CONTENT_BACKGROUND_COLOR
	)

	/**
	 * Initialize end scene view
	 */
	init {
		backgroundButton.isDisabled = true
		player3NameLabel.isVisible = false
		player3Score.isVisible = false
		player4NameLabel.isVisible = false
		player4Score.isVisible = false

		addComponents(
			backgroundButton,
			scoreLabel,
			player1NameLabel,
			player1Score,
			player2NameLabel,
			player2Score,
			player3NameLabel,
			player3Score,
			player4NameLabel,
			player4Score,
			newGameButton,
			quitButton,
			newGameWithSamePlayerButton,
		)
	}
}