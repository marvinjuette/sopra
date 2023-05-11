package view

import entity.Card
import extensions.onButtonClicked
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

class GameScene(val rootService: RootService): BoardGameScene(
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

	override fun refreshAfterGameStart() {
		val currentPlayer = rootService.gameState.players[0]
		playerNameLabel.text = currentPlayer.name
		passCounterLabel.text = "Pass: ${rootService.gameState.passCounter} / ${rootService.gameState.players.size}"

		centralCardsLinearLayout.forEach { it.removeFromParent() }
		rootService.gameState.centralCards.forEach { centralCardsLinearLayout.add(getCardImage(it)) }
		centralCardsLinearLayout.forEach { it.flip() }

		centralStackCardStack.forEach { it.removeFromParent() }
		rootService.gameState.stackCards.forEach { centralStackCardStack.add(getCardImage(it)) }

		handCardsLinearLayout.forEach { it.removeFromParent() }
		currentPlayer.handCards.forEach { handCardsLinearLayout.add(getCardImage(it)) }
	}

	override fun refreshAfterPlayerChange() {
		val player = rootService.gameState.players[rootService.gameState.currentPlayer]
		playerNameLabel.text = player.name

		handCardsLinearLayout.removeAll { true }
		player.handCards.forEach { handCardsLinearLayout.add(getCardImage(it)) }
	}

	override fun refreshAfterPass() {
		passCounterLabel.text = "Pass: ${rootService.gameState.passCounter} / ${rootService.gameState.players.size}"
	}

	override fun refreshAfterPassWithCardsExchanged() {
		centralStackLabel.text = "Central Stack (${rootService.gameState.stackCards.size})"
		passCounterLabel.text = "Pass: ${rootService.gameState.passCounter} / ${rootService.gameState.players.size}"

		centralCardsLinearLayout.removeAll { true }
		rootService.gameState.centralCards.forEach { centralCardsLinearLayout.add(getCardImage(it)) }
		centralCardsLinearLayout.forEach { it.flip() }
	}

	override fun refreshAfterPlayerRevealedCards() {
		handCardsLinearLayout.forEach { it.flip() }

		val handCards = rootService.gameState.players[rootService.gameState.currentPlayer].handCards
		pointsLabel.text = "Points: ${rootService.gameService.calculateScore(handCards)}"
	}

	// TODO: implement changeAllCards
	// TODO: implement changeSingleCard
	// TODO: implement knock

	init {
		infoBackgroundButton.isDisabled = true

		// TODO: implement way to switch two cards
		changeCardButton.onButtonClicked { rootService.playerActionService.changeCard(0, 0) }
		changeAllCardsButton.onButtonClicked { rootService.playerActionService.changeAllCards() }
		passButton.onButtonClicked { rootService.playerActionService.pass() }
		knockButton.onButtonClicked { rootService.playerActionService.knock() }

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