package view

import entity.Card
import entity.CardSuit
import entity.CardValue
import service.CardImageLoader
import service.RootService
import tools.aqua.bgw.components.container.CardStack
import tools.aqua.bgw.components.container.LinearLayout
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.visual.ImageVisual
import utils.Colors.CONTENT_BACKGROUND_COLOR
import utils.Colors.WHITE
import utils.Colors.WINDOW_BACKGROUND_COLOR
import utils.Constants.ACTIONS_TEXT
import utils.Constants.ACTION_BUTTON_FONT
import utils.Constants.CARD_HEIGHT
import utils.Constants.CARD_LINEAR_LAYOUT_HEIGHT
import utils.Constants.CARD_LINEAR_LAYOUT_SPACING
import utils.Constants.CARD_LINEAR_LAYOUT_WIDTH
import utils.Constants.CARD_WIDTH
import utils.Constants.CENTRAL_CARDS_TEXT
import utils.Constants.CHANGE_ALL_CARDS_BUTTON_TEXT
import utils.Constants.CHANGE_CARD_BUTTON_TEXT
import utils.Constants.DEFAULT_MARGIN
import utils.Constants.DEFAULT_SPACING
import utils.Constants.DEFAULT_WINDOW_HEIGHT
import utils.Constants.DEFAULT_WINDOW_WIDTH
import utils.Constants.HAND_CARDS_TEXT
import utils.Constants.KNOCK_BUTTON_TEXT
import utils.Constants.LABEL_FONT
import utils.Constants.PASS_BUTTON_TEXT
import utils.Constants.TEXT_FIELD_HEIGHT
import utils.Constants.TEXT_FIELD_WIDTH

class GameScene(rootService: RootService): BoardGameScene(
	width = DEFAULT_WINDOW_WIDTH,
	height= DEFAULT_WINDOW_HEIGHT,
	background = WINDOW_BACKGROUND_COLOR
), Refreshable {

	private val cardImageLoader = CardImageLoader()

	private val pointsLabel = Label(
		posX = DEFAULT_MARGIN,
		posY = DEFAULT_MARGIN/4,
		width = TEXT_FIELD_WIDTH,
		height = TEXT_FIELD_HEIGHT,
		font = LABEL_FONT,
		text = "Points: ",
		alignment = Alignment.CENTER_LEFT
	)

	private val playerNameLabel = Label(
		posX = 0,
		posY = pointsLabel.posY,
		width = width - DEFAULT_MARGIN,
		height = TEXT_FIELD_HEIGHT,
		font = LABEL_FONT,
		text = "Spieler 1",
		alignment = Alignment.CENTER
	)

	private val passCounterLabel = Label(
		posX = 0,
		posY = pointsLabel.posY,
		width = width - DEFAULT_MARGIN - TEXT_FIELD_WIDTH,
		height = TEXT_FIELD_HEIGHT,
		font = LABEL_FONT,
		text = "Pass",
		alignment = Alignment.CENTER_RIGHT
	)

	private val infoBackgroundButton = Button(
		posX = pointsLabel.posX - DEFAULT_MARGIN / 2,
		posY = pointsLabel.posY - DEFAULT_MARGIN / 8 ,
		width = width - DEFAULT_MARGIN,
		height = TEXT_FIELD_HEIGHT + DEFAULT_MARGIN / 4,
		visual = CONTENT_BACKGROUND_COLOR
	)

	private val centralCardsLabel = Label(
		posX = pointsLabel.posX - DEFAULT_MARGIN / 2,
		posY = infoBackgroundButton.posY + infoBackgroundButton.height + DEFAULT_SPACING,
		width = TEXT_FIELD_WIDTH,
		height = TEXT_FIELD_HEIGHT,
		font = LABEL_FONT,
		text = CENTRAL_CARDS_TEXT,
		alignment = Alignment.CENTER_LEFT
	)

	private val centralCardsLinearLayout = LinearLayout<CardView>(
		posX = centralCardsLabel.posX,
		posY = centralCardsLabel.posY + centralCardsLabel.height,
		width = CARD_LINEAR_LAYOUT_WIDTH,
		height = CARD_LINEAR_LAYOUT_HEIGHT,
		spacing = CARD_LINEAR_LAYOUT_SPACING,
		alignment = Alignment.CENTER,
		visual = CONTENT_BACKGROUND_COLOR
	)

	private val handCardsLabel = Label(
		posX = centralCardsLabel.posX,
		posY = centralCardsLinearLayout.posY + centralCardsLinearLayout.height + DEFAULT_SPACING,
		width = TEXT_FIELD_WIDTH,
		height = TEXT_FIELD_HEIGHT,
		font = LABEL_FONT,
		text = HAND_CARDS_TEXT,
		alignment = Alignment.CENTER_LEFT
	)

	private val handCardsLinearLayout = LinearLayout<CardView>(
		posX = handCardsLabel.posX,
		posY = handCardsLabel.posY + centralCardsLabel.height,
		width = CARD_LINEAR_LAYOUT_WIDTH,
		height = CARD_LINEAR_LAYOUT_HEIGHT,
		spacing = CARD_LINEAR_LAYOUT_SPACING,
		alignment = Alignment.CENTER,
		visual = CONTENT_BACKGROUND_COLOR
	)

	private val centralStackLabel = Label(
		posX = centralCardsLabel.posX + centralCardsLinearLayout.width + DEFAULT_MARGIN,
		posY = centralCardsLabel.posY,
		width = TEXT_FIELD_WIDTH,
		height = TEXT_FIELD_HEIGHT,
		font = LABEL_FONT,
		text = CENTRAL_CARDS_TEXT,
		alignment = Alignment.CENTER_LEFT
	)

	private val centralStackCardStack = CardStack<CardView>(
		posX = centralStackLabel.posX,
		posY = centralStackLabel.posY + centralStackLabel.height,
		width = CARD_LINEAR_LAYOUT_WIDTH,
		height = CARD_LINEAR_LAYOUT_HEIGHT,
		alignment = Alignment.CENTER,
		visual = CONTENT_BACKGROUND_COLOR
	)

	private val actionsLabel = Label(
		posX = handCardsLinearLayout.posX + handCardsLinearLayout.width + DEFAULT_MARGIN,
		posY = handCardsLabel.posY,
		width = TEXT_FIELD_WIDTH,
		height = TEXT_FIELD_HEIGHT,
		font = LABEL_FONT,
		text = ACTIONS_TEXT,
		alignment = Alignment.CENTER_LEFT
	)

	private val changeCardButton = Button(
		posX = actionsLabel.posX,
		posY = actionsLabel.posY + actionsLabel.height,
		width = CARD_LINEAR_LAYOUT_WIDTH / 2 - DEFAULT_SPACING / 2,
		height = CARD_LINEAR_LAYOUT_HEIGHT / 2 - DEFAULT_SPACING / 2,
		text = CHANGE_CARD_BUTTON_TEXT,
		font = ACTION_BUTTON_FONT,
		visual = WHITE,
	)

	private val changeAllCardsButton = Button(
		posX = actionsLabel.posX,
		posY = changeCardButton.posY + changeCardButton.height + DEFAULT_SPACING,
		width = CARD_LINEAR_LAYOUT_WIDTH / 2 - DEFAULT_SPACING / 2,
		height = CARD_LINEAR_LAYOUT_HEIGHT / 2 - DEFAULT_SPACING / 2,
		text = CHANGE_ALL_CARDS_BUTTON_TEXT,
		font = ACTION_BUTTON_FONT,
		visual = WHITE,
	)

	private val passButton = Button(
		posX = changeCardButton.posX + changeCardButton.width + DEFAULT_SPACING,
		posY = actionsLabel.posY + actionsLabel.height,
		width = CARD_LINEAR_LAYOUT_WIDTH / 2 - DEFAULT_SPACING / 2,
		height = CARD_LINEAR_LAYOUT_HEIGHT / 2 - DEFAULT_SPACING / 2,
		text = PASS_BUTTON_TEXT,
		font = ACTION_BUTTON_FONT,
		visual = WHITE,
	)

	private val knockButton = Button(
		posX = passButton.posX,
		posY = changeAllCardsButton.posY,
		width = CARD_LINEAR_LAYOUT_WIDTH / 2 - DEFAULT_SPACING / 2,
		height = CARD_LINEAR_LAYOUT_HEIGHT / 2 - DEFAULT_SPACING / 2,
		text = KNOCK_BUTTON_TEXT,
		font = ACTION_BUTTON_FONT,
		visual = WHITE,
	)

	fun getCardImage(card: Card) = CardView(
		width = CARD_WIDTH,
		height = CARD_HEIGHT,
		front = ImageVisual(cardImageLoader.frontImageFor(card.suit, card.value)),
		back = ImageVisual(cardImageLoader.backImage)
	)

	init {
		centralCardsLinearLayout.addAll(
			getCardImage(Card(CardSuit.CLUBS, CardValue.ACE)),
			getCardImage(Card(CardSuit.CLUBS, CardValue.KING)),
			getCardImage(Card(CardSuit.CLUBS, CardValue.TEN)),
		)
		centralCardsLinearLayout.forEach { it.flip() }

		handCardsLinearLayout.addAll(
			getCardImage(Card(CardSuit.HEARTS, CardValue.ACE)),
			getCardImage(Card(CardSuit.HEARTS, CardValue.KING)),
			getCardImage(Card(CardSuit.HEARTS, CardValue.TEN)),
		)
		handCardsLinearLayout.forEach { it.flip() }

		centralStackCardStack.addAll(
			getCardImage(Card(CardSuit.DIAMONDS, CardValue.ACE)),
			getCardImage(Card(CardSuit.DIAMONDS, CardValue.KING)),
			getCardImage(Card(CardSuit.DIAMONDS, CardValue.TEN)),
		)

		infoBackgroundButton.isDisabled = true

		addComponents(
			infoBackgroundButton,
			pointsLabel,
			playerNameLabel,
			passCounterLabel,
			centralCardsLabel,
			centralCardsLinearLayout,
			handCardsLabel,
			handCardsLinearLayout,
			centralStackLabel,
			centralStackCardStack,
			actionsLabel,
			changeCardButton,
			changeAllCardsButton,
			passButton,
			knockButton
		)
	}
}