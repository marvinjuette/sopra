package entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.TestUtils.getListOfCards
import utils.TestUtils.getPlayerList

/**
 * This class contains tests to test the functionality of the [GameState] data class
 */
class GameStateTest {

	private lateinit var gameState: GameState
	private val passCounter = 0
	private val currentPlayer = 0
	private val stackCards = getListOfCards(17)
	private val centralCards = getListOfCards(3)
	private val playerList = getPlayerList()

	/**
	 * Recreate gameState before every test to ensure that before each test it has the same initial data
	 */
	@BeforeEach
	fun init() {
		gameState = GameState(passCounter, currentPlayer, stackCards, centralCards, playerList)
	}

	/**
	 * Tests if the returned passCounter by the gameState is equal to the initial passCounter
	 */
	@Test
	fun `test if returned passCounter by gameState is equal to initial one`() {
		assertEquals(passCounter, gameState.passCounter)
	}

	/**
	 * Tests if the reassignment of the passCounter works properly
	 */
	@Test
	fun `test if passCounter is re-assignable`() {
		val newPassCounter = 2

		gameState.passCounter = newPassCounter

		assertEquals(newPassCounter, gameState.passCounter)
	}

	/**
	 * Tests if the returned currentPlayerIndex by the gameState is equal to the initial currentPlayerIndex
	 */
	@Test
	fun `test if returned currentPlayer is equal to initial one`() {
		assertEquals(currentPlayer, gameState.currentPlayer)
	}

	/**
	 * Tests if the reassignment of the currentPlayerIndex works properly
	 */
	@Test
	fun `test if currentPlayer is re-assignable`() {
		val newCurrentPlayerIndex = 3

		gameState.currentPlayer = newCurrentPlayerIndex

		assertEquals(newCurrentPlayerIndex, gameState.currentPlayer)
	}

	/**
	 * Tests if the returned stackCards by the gameState is equal to the initial stackCards
	 */
	@Test
	fun `test if returned stackCards are equal to initial ones`() {
		assertEquals(stackCards, gameState.stackCards)
	}

	/**
	 * Tests if single cards in the stack are removable
	 */
	@Test
	fun `test if cards in stack cards are removable`() {
		val initialStackSize = gameState.stackCards.size

		gameState.stackCards.removeFirst()

		assertThat(gameState.stackCards.size).isLessThan(initialStackSize)
	}

	/**
	 * Tests if the returned centralCards by the gameState is equal to the initial centralCards
	 */
	@Test
	fun `test if returned centralCards are equal to initial ones`() {
		assertEquals(centralCards, gameState.centralCards)
	}

	/**
	 * Tests if the reassignment of the centralCards works properly
	 */
	@Test
	fun `test if centralCards are re-assignable`() {
		val newCentralCards = getListOfCards(3)

		gameState.centralCards = newCentralCards

		assertEquals(newCentralCards, gameState.centralCards)
	}

	/**
	 * Tests if the centralCards are exchangeable
	 */
	@Test
	fun `test if single centralCards can be exchanged`() {
		val centralCard1 = Card(CardSuit.HEARTS, CardValue.SEVEN)
		val centralCard2 = Card(CardSuit.HEARTS, CardValue.EIGHT)
		val centralCard3 = Card(CardSuit.HEARTS, CardValue.NINE)
		val centralCards = mutableListOf(centralCard1, centralCard2, centralCard3)
		gameState.centralCards = centralCards

		val newCentralCard1 = Card(CardSuit.SPADES, CardValue.ACE)
		val newCentralCard2 = Card(CardSuit.SPADES, CardValue.KING)
		val newCentralCard3 = Card(CardSuit.SPADES, CardValue.QUEEN)

		gameState.centralCards[0] = newCentralCard1
		gameState.centralCards[1] = newCentralCard2
		gameState.centralCards[2] = newCentralCard3

		assertThat(gameState.centralCards).containsOnly(newCentralCard1, newCentralCard2, newCentralCard3)
		assertThat(gameState.centralCards).doesNotContainAnyElementsOf(listOf(centralCard1, centralCard2, centralCard3))

	}

	/**
	 * Tests if the returned playerList by the gameState is equal to the initial playerList
	 */
	@Test
	fun `test if returned playerList is equal to initial one`() {
		assertEquals(playerList, gameState.players)
	}
}