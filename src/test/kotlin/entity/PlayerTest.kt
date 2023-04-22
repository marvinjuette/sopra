package entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import utils.TestUtils.getListOfCards
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * This class contains tests to ensure the proper functionality of the player data class
 */
class PlayerTest {

	/**
	 * This method tests multiple string inputs for the player name
	 *
	 * Null is not an option here because of kotlin's null safety
	 */
	@ParameterizedTest(name = "player.name should be equal to {0}")
	@ValueSource(strings = ["Player 1", "Player 2", "Player 3", "Player 4", "", " "])
	fun getName(name: String) {
		val player = Player(name, false, getListOfCards(3))
		assertEquals(name, player.name)
	}

	/**
	 * This method tests all boolean inputs for player hasKnocked
	 */
	@ParameterizedTest(name = "player.hasKnocked should be equal to {0}")
	@ValueSource(booleans = [true, false])
	fun getHasKnocked(hasKnocked: Boolean) {
		val player = Player("Player 1", hasKnocked, getListOfCards(3))
		assertEquals(hasKnocked, player.hasKnocked)
	}

	/**
	 * This method tests if the returned handCards by the player object is equal to it's initial handCards
	 */
	@Test
	fun `test if returned handCards by player are equal to initial handCards`() {
		val handCards = getListOfCards(3)
		val player = Player("Player 1", false, handCards)

		assertEquals(handCards, player.handCards)
	}

	/**
	 * This method tests if the reassignment of the handCards works properly
	 */
	@Test
	fun `test if handCards are re-assignable`() {
		val initialHandCards = getListOfCards(3)
		val newHandCards = getListOfCards(3)
		val player = Player("Player 1", false, initialHandCards)

		player.handCards = newHandCards

		assertEquals(newHandCards, player.handCards)
	}

	/**
	 * This method tests if each single hand card can be exchanged by a new one
	 */
	@Test
	fun `tests if are handCards exchangeable`() {
		val card1 = Card(CardSuit.CLUBS, CardValue.SEVEN)
		val card2 = Card(CardSuit.CLUBS, CardValue.EIGHT)
		val card3 = Card(CardSuit.CLUBS, CardValue.NINE)
		val handCards = mutableListOf(card1, card2, card3)

		val player = Player("Player 1", false, handCards)

		val newCard1 = Card(CardSuit.DIAMONDS, CardValue.ACE)
		val newCard2 = Card(CardSuit.DIAMONDS, CardValue.KING)
		val newCard3 = Card(CardSuit.DIAMONDS, CardValue.QUEEN)

		player.handCards[0] = newCard1
		player.handCards[1] = newCard2
		player.handCards[2] = newCard3

		assertThat(player.handCards).containsOnly(newCard1, newCard2, newCard3)
		assertThat(player.handCards).doesNotContainAnyElementsOf(listOf(card1, card2, card3))
	}
}