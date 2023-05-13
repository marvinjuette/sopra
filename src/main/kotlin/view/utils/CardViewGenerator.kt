package view.utils

import entity.Card
import models.Constants
import service.CardImageLoader
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.visual.ImageVisual

/**
 * Helper object containing a collection of functions to make it easier to generate card views based on the input card
 */
object CardViewGenerator {
	private val cardImageLoader = CardImageLoader()

	/**
	 * Generates a card view (with the back facing up) of the given card.
	 *
	 * @param card Reference of a [Card] which should be displayed
	 * @return A [CardView] of the given card
	 */
	fun generateCardImage(card: Card) = CardView(
		posX = 0.0,
		posY = 0.0,
		width = Constants.CARD_WIDTH,
		height = Constants.CARD_HEIGHT,
		front = ImageVisual(cardImageLoader.frontImageFor(card.suit, card.value)),
		back = ImageVisual(cardImageLoader.backImage)
	)

	/**
	 * Generates a card view (with the front facing up) of the given card
	 *
	 * @param card Reference of a [Card] which should be displayed
	 * @return A [CardView] of the given card
	 */
	fun generateFlippedCardImage(card: Card): CardView {
		val cardView = generateCardImage(card)
		cardView.showCardSide(CardView.CardSide.FRONT)
		return cardView
	}
}