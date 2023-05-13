package view.scenes

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.core.MenuScene
import models.Colors.CONTENT_BACKGROUND_COLOR
import models.Colors.CYAN
import models.Colors.RED
import models.Colors.WINDOW_BACKGROUND_COLOR
import models.Constants.DEFAULT_MARGIN
import models.Constants.DEFAULT_SPACING
import models.Constants.DEFAULT_WINDOW_HEIGHT
import models.Constants.DEFAULT_WINDOW_WIDTH
import models.Constants.MENU_BUTTON_FONT
import models.Constants.QUIT_BUTTON_TEXT
import models.Constants.START_BUTTON_TEXT
import models.Constants.TEXT_FIELD_HEIGHT
import models.Constants.TEXT_FIELD_WIDTH
import view.utils.ViewPositioning.calculateCenterX
import view.utils.ViewPositioning.calculateCenterY
import java.awt.Desktop
import java.net.URL

class StartScene: MenuScene(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT) {

    internal fun getSceneWidth() = this.width

    internal val contentHeight = 5 * TEXT_FIELD_HEIGHT.toDouble() + 4 * DEFAULT_SPACING
    private val buttonWidth = (TEXT_FIELD_WIDTH - DEFAULT_SPACING/2) / 2

    internal val playerName1 = TextField(
        posX = calculateCenterX(width, TEXT_FIELD_WIDTH.toDouble()),
        posY = (height - contentHeight) / 2,
        width = TEXT_FIELD_WIDTH,
        height = TEXT_FIELD_HEIGHT,
        prompt = "Player 1"
    )

    internal val playerName2 = TextField(
        posX = calculateCenterX(width, TEXT_FIELD_WIDTH.toDouble()),
        posY = playerName1.posY + TEXT_FIELD_HEIGHT + DEFAULT_SPACING,
        width = TEXT_FIELD_WIDTH,
        height = TEXT_FIELD_HEIGHT,
        prompt = "Player 2"
    )

    internal val playerName3 = TextField(
        posX = calculateCenterX(width, TEXT_FIELD_WIDTH.toDouble()),
        posY = playerName2.posY + TEXT_FIELD_HEIGHT +  DEFAULT_SPACING,
        width = TEXT_FIELD_WIDTH,
        height = TEXT_FIELD_HEIGHT,
        prompt = "Player 3 [Optional]"
    )

    internal val playerName4 = TextField(
        posX = calculateCenterX(width, TEXT_FIELD_WIDTH.toDouble()),
        posY = playerName3.posY + TEXT_FIELD_HEIGHT + DEFAULT_SPACING,
        width = TEXT_FIELD_WIDTH,
        height = TEXT_FIELD_HEIGHT,
        prompt = "Player 4 [Optional]"
    )

    internal val backgroundButton = Button(
        posX = calculateCenterX(width, TEXT_FIELD_WIDTH.toDouble()) - 0.5 * DEFAULT_MARGIN,
        posY = calculateCenterY(height, contentHeight) - 0.5 * DEFAULT_MARGIN,
        width = TEXT_FIELD_WIDTH + DEFAULT_MARGIN,
        height = contentHeight + DEFAULT_MARGIN,
        visual = CONTENT_BACKGROUND_COLOR
    ).apply { onMouseClicked = {
        // never gonna give you up!
        Desktop.getDesktop().browse(URL("https://www.youtube.com/watch?v=dQw4w9WgXcQ").toURI())
    } }

    internal val errorLabel = Label()

    internal val startButton = Button(
        posX = calculateCenterX(width, TEXT_FIELD_WIDTH.toDouble()),
        posY = playerName4.posY + TEXT_FIELD_HEIGHT + DEFAULT_SPACING,
        width = buttonWidth,
        height = TEXT_FIELD_HEIGHT,
        text = START_BUTTON_TEXT,
        font = MENU_BUTTON_FONT,
        visual = CYAN
    )

    internal val quitButton = Button(
        posX = startButton.posX + startButton.width + DEFAULT_SPACING/2,
        posY = startButton.posY,
        width = buttonWidth,
        height = TEXT_FIELD_HEIGHT,
        text = QUIT_BUTTON_TEXT,
        font = MENU_BUTTON_FONT,
        visual = RED
    )

    init {
        background = WINDOW_BACKGROUND_COLOR
        addComponents(
            backgroundButton,
            playerName1,
            playerName2,
            playerName3,
            playerName4,
            startButton,
            quitButton,
            errorLabel
        )
    }
}