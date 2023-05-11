package view

import entity.Card
import service.CardImageLoader
import service.RootService
import tools.aqua.bgw.components.container.LinearLayout
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.visual.ImageVisual
import utils.Colors.WINDOW_BACKGROUND_COLOR
import utils.Constants.DEFAULT_WINDOW_HEIGHT
import utils.Constants.DEFAULT_WINDOW_WIDTH

class GameScene(rootService: RootService): BoardGameScene(
	width = DEFAULT_WINDOW_WIDTH,
	height= DEFAULT_WINDOW_HEIGHT,
	background = WINDOW_BACKGROUND_COLOR
), Refreshable {

	private val cardImageLoader = CardImageLoader()

	private val centralCardsLinearLayout = LinearLayout<CardView>(

	)

	private val handCardsLinearLayout = LinearLayout<CardView>(

	)

	fun getCardImage(card: Card) = CardView(
		height = 250,
		width = 150,
		front = ImageVisual(cardImageLoader.frontImageFor(card.suit, card.value)),
		back = ImageVisual(cardImageLoader.backImage)
	)

}