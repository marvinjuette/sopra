package models

import tools.aqua.bgw.core.DEFAULT_CARD_HEIGHT
import tools.aqua.bgw.core.DEFAULT_CARD_WIDTH
import tools.aqua.bgw.util.Font
import models.Colors.CYAN
import java.awt.Color

object Constants {
    // COMMON
    const val WINDOW_TITLE = "SoPra Schwimmen by Marvin Jütte"
    const val DEFAULT_WINDOW_WIDTH = 1920
    const val DEFAULT_WINDOW_HEIGHT = 1080
    const val TEXT_FIELD_WIDTH = 250
    const val TEXT_FIELD_HEIGHT = 50
    const val DEFAULT_SPACING = 60
    const val DEFAULT_MARGIN = 150
    val LABEL_FONT = Font(size = 24, color = Colors.LABEL_TEXT_COLOR.color)

    // MENU SCENES
    val MENU_BUTTON_FONT = Font(size = 20, fontWeight = Font.FontWeight.BOLD, color = Color.WHITE)
    const val QUIT_BUTTON_TEXT = "Quit"

    // START SCENE
    const val START_BUTTON_TEXT = "Start"
    const val AT_LEAST_TWO_PLAYERS_ARE_REQUIRED_ERROR_MESSAGE = "Please provide at least two player names!"
    val TITLE_LABEL_FONT = Font(size = 24, fontWeight = Font.FontWeight.BOLD, color = CYAN.color)

    // GAME SCENE
    const val CENTRAL_CARDS_TEXT = "Central Cards"
    const val HAND_CARDS_TEXT = "Hand Cards"
    const val ACTIONS_TEXT = "Actions"
    const val CHANGE_CARD_BUTTON_TEXT = "Change\n  Card"
    const val CHANGE_ALL_CARDS_BUTTON_TEXT = " Change\nall Cards"
    const val PASS_BUTTON_TEXT = "Pass"
    const val KNOCK_BUTTON_TEXT = "Knock"
    const val CARD_WIDTH = 1 * DEFAULT_CARD_WIDTH
    const val CARD_HEIGHT = 1 * DEFAULT_CARD_HEIGHT
    const val CARD_LINEAR_LAYOUT_WIDTH = 3 * CARD_WIDTH + 4 * DEFAULT_SPACING + DEFAULT_MARGIN
    const val CARD_LINEAR_LAYOUT_HEIGHT = CARD_HEIGHT + DEFAULT_MARGIN
    const val CARD_LINEAR_LAYOUT_SPACING = 2 * DEFAULT_SPACING
    val ACTION_BUTTON_FONT = Font(size = 24, fontWeight = Font.FontWeight.BOLD)

    // PLAYER CHANGE SCENE
    const val PRESS_KEY_TO_SHOW_CARDS_TEXT = "press any key to reveal your cards"

    // END SCENE
    const val SCORE_TEXT = "Score"
    const val NEW_GAME_BUTTON_TEXT = "New"
    const val NEW_GAME_WITH_SAME_PLAYERS_BUTTON_TEXT = "Play with same Players"
    val PLAYER_NAME_FONT = Font(size = 20, color = CYAN.color)
    val SCORE_FONT = Font(size = 18, fontStyle = Font.FontStyle.ITALIC)

    // NO HAND CARD SELECTED DIALOG
    const val NO_HAND_CARD_SELECTED_DIALOG_TITLE = "Invalid move"
    const val NO_HAND_CARD_SELECTED_DIALOG_HEADER = "No hand card selected"
    const val NO_HAND_CARD_SELECTED_DIALOG_MESSAGE = "Please select a hand card by clicking on it"

    // NO CENTRAL CARD SELECTED DIALOG
    const val NO_CENTRAL_CARD_SELECTED_DIALOG_TITLE = "Invalid move"
    const val NO_CENTRAL_CARD_SELECTED_DIALOG_HEADER = "No central card selected"
    const val NO_CENTRAL_CARD_SELECTED_DIALOG_MESSAGE = "Please select a central card by clicking on it"
}