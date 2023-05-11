package view

import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import utils.Colors.CONTENT_BACKGROUND_COLOR
import utils.Colors.CYAN
import utils.Colors.RED
import utils.Colors.WINDOW_BACKGROUND_COLOR
import utils.Constants.AT_LEAST_TWO_PLAYERS_ARE_REQUIRED_ERROR_MESSAGE
import utils.Constants.DEFAULT_MARGIN
import utils.Constants.DEFAULT_SPACING
import utils.Constants.DEFAULT_WINDOW_HEIGHT
import utils.Constants.DEFAULT_WINDOW_WIDTH
import utils.Constants.QUIT_BUTTON_TEXT
import utils.Constants.START_BUTTON_TEXT
import utils.Constants.TEXT_FIELD_HEIGHT
import utils.Constants.TEXT_FIELD_WIDTH
import utils.Utils.calculateCenterX
import utils.Utils.calculateCenterY
import java.awt.Color
import java.awt.Desktop
import java.net.URL
import kotlin.system.exitProcess

class StartScene(rootService: RootService) : MenuScene(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT) {

    private fun getSceneWidth() = this.width

    private val contentHeight = 5 * TEXT_FIELD_HEIGHT.toDouble() + 4 * DEFAULT_SPACING
    private val buttonWidth = (TEXT_FIELD_WIDTH - DEFAULT_SPACING/2) / 2

    private val playerName1 = TextField(
        posX = calculateCenterX(width, TEXT_FIELD_WIDTH.toDouble()),
        posY = (height - contentHeight) / 2,
        width = TEXT_FIELD_WIDTH,
        height = TEXT_FIELD_HEIGHT,
        prompt = "Player 1"
    )

    private val playerName2 = TextField(
        posX = calculateCenterX(width, TEXT_FIELD_WIDTH.toDouble()),
        posY = playerName1.posY + TEXT_FIELD_HEIGHT + DEFAULT_SPACING,
        width = TEXT_FIELD_WIDTH,
        height = TEXT_FIELD_HEIGHT,
        prompt = "Player 2"
    )

    private val playerName3 = TextField(
        posX = calculateCenterX(width, TEXT_FIELD_WIDTH.toDouble()),
        posY = playerName2.posY + TEXT_FIELD_HEIGHT +  DEFAULT_SPACING,
        width = TEXT_FIELD_WIDTH,
        height = TEXT_FIELD_HEIGHT,
        prompt = "Player 3 [Optional]"
    )

    private val playerName4 = TextField(
        posX = calculateCenterX(width, TEXT_FIELD_WIDTH.toDouble()),
        posY = playerName3.posY + TEXT_FIELD_HEIGHT + DEFAULT_SPACING,
        width = TEXT_FIELD_WIDTH,
        height = TEXT_FIELD_HEIGHT,
        prompt = "Player 4 [Optional]"
    )

    // we use a button instead because Moritz found out, that this way the first TextField isn't auto selected
    // by BGW (WTF?!)
    // because that we won't disable the background button
    private val backgroundButton = Button(
        posX = calculateCenterX(width, TEXT_FIELD_WIDTH.toDouble()) - 0.5 * DEFAULT_MARGIN,
        posY = calculateCenterY(height, contentHeight) - 0.5 * DEFAULT_MARGIN,
        width = TEXT_FIELD_WIDTH + DEFAULT_MARGIN,
        height = contentHeight + DEFAULT_MARGIN,
        visual = CONTENT_BACKGROUND_COLOR
    ).apply { onMouseClicked = {
        // never gonna give you up!
        Desktop.getDesktop().browse(URL("https://www.youtube.com/watch?v=dQw4w9WgXcQ").toURI())
    } }

    private val errorLabel = Label()

    private val startButton = Button(
        posX = calculateCenterX(width, TEXT_FIELD_WIDTH.toDouble()),
        posY = playerName4.posY + TEXT_FIELD_HEIGHT + DEFAULT_SPACING,
        width = buttonWidth,
        height = TEXT_FIELD_HEIGHT,
        text = START_BUTTON_TEXT,
        font = Font(size = 20, fontWeight = Font.FontWeight.BOLD, color = Color.WHITE),
        visual = CYAN
    ).apply { onMouseClicked = {
        if (playerName1.text.isBlank() || playerName2.text.isBlank()) {
            showErrorLabel()
        } else {
            rootService.gameService.startNewGame(getPlayerNamesAsList())
        }
    } }

    private fun getPlayerNamesAsList(): List<String> {
        val playerNames = mutableListOf<String>()
        playerNames.add(playerName1.text.trim())
        playerNames.add(playerName2.text.trim())

        if (playerName3.text.isNotBlank()) {
            playerNames.add(playerName3.text.trim())
        }

        if (playerName4.text.isNotBlank()) {
            playerNames.add(playerName4.text.trim())
        }

        return playerNames.toList()
    }

    private fun showErrorLabel() {
        errorLabel.posX = .0
        errorLabel.posY = backgroundButton.posY + contentHeight + DEFAULT_MARGIN + DEFAULT_SPACING
        errorLabel.width = getSceneWidth()
        errorLabel.text = AT_LEAST_TWO_PLAYERS_ARE_REQUIRED_ERROR_MESSAGE
        errorLabel.font = Font(size = 24, color = Color.RED)
        errorLabel.alignment = Alignment.BOTTOM_CENTER
    }

    private val quitButton = Button(
        posX = startButton.posX + startButton.width + DEFAULT_SPACING/2,
        posY = startButton.posY,
        width = buttonWidth,
        height = TEXT_FIELD_HEIGHT,
        text = QUIT_BUTTON_TEXT,
        font = Font(size = 20, fontWeight = Font.FontWeight.BOLD, color = Color.WHITE),
        visual = RED
    ).apply { onMouseClicked = {
         exitProcess(0)
    } }

    init {
        background = WINDOW_BACKGROUND_COLOR
        addComponents(backgroundButton, playerName1, playerName2, playerName3, playerName4, startButton, quitButton, errorLabel)
    }
}