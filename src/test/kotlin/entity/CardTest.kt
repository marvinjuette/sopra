package entity

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import kotlin.test.assertEquals

/**
 * This class contains tests to test the functionality of the [Card] data class
 */
class CardTest {

	/**
	 * This test tests all possible [CardSuit] enum values for the current card to be returned
	 *
	 * @param cardSuit is a value of the [CardSuit] enum
	 */
	@EnumSource
	@ParameterizedTest(name = "card.suit should equal to {0}")
	fun getCardSuit(cardSuit: CardSuit) {
		val card = Card(cardSuit, CardValue.ACE)
		assertEquals(card.suit, cardSuit)
	}


	/**
	 * This test tests all possible [CardValue] enum values for the current card to be returned
	 *
	 * @param cardValue is a value of the [CardValue] enum
	 */
	@EnumSource
	@ParameterizedTest(name = "card.value should equal to {0}")
	fun getCardValue(cardValue: CardValue) {
		val card = Card(CardSuit.CLUBS, cardValue)
		assertEquals(card.value, cardValue)
	}
}