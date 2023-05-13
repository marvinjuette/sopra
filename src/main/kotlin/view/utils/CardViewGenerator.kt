package view.utils

import entity.Card
import models.Constants
import service.CardImageLoader
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.visual.ImageVisual

object CardViewGenerator {
	private val cardImageLoader = CardImageLoader()

	fun generateCardImage(card: Card) = CardView(
		posX = 0.0,
		posY = 0.0,
		width = Constants.CARD_WIDTH,
		height = Constants.CARD_HEIGHT,
		front = ImageVisual(cardImageLoader.frontImageFor(card.suit, card.value)),
		back = ImageVisual(cardImageLoader.backImage)
	)

	fun generateFlippedCardImage(card: Card): CardView {
		val cardView = generateCardImage(card)
		cardView.showCardSide(CardView.CardSide.FRONT)
		return cardView
	}
}