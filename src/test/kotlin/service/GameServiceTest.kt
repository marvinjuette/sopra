package service

import entity.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import utils.TestUtils.getListOfCards

/**
 * This class contains all unit tests for the [GameService]
 *
 * @property rootService Mock object of the [RootService]
 * @property gameState Mock object of the [GameState]
 * @property playerActionService Mock object of the [PlayerActionService]
 * @property gameService Instance of the [GameService]
 */
@ExtendWith(MockitoExtension::class)
class GameServiceTest {

	@Mock
	private lateinit var rootService: RootService
	@Mock
	private lateinit var gameState: GameState
	@Mock
	private lateinit var playerActionService: PlayerActionService

	private lateinit var gameService: GameService


	/**
	 * This method runs before each test to make sure that we have a fresh [GameService]
	 */
	@BeforeEach
	fun init() {
		gameService = GameService(rootService)
	}

	/**
	 * Tests if the game state is properly initialized by the start new game method
	 */
	@Test
	fun `start new game properly resets the game state`() {
		gameService = GameServiceStub(rootService)

		doReturn(gameState).`when`(rootService).gameState
		doReturn(playerActionService).`when`(rootService).playerActionService

		val playerMock: Player = mock()

		doNothing().`when`(playerActionService).resetPassCount()
		`when`(gameState.stackCards).thenReturn(getListOfCards(32))
		`when`(gameState.players).thenReturn(listOf(playerMock))
		`when`(playerMock.hasKnocked).thenReturn(true)

		gameService.startNewGame()

		verify(playerActionService, times(1)).resetPassCount()
		verify(gameState, times(1)).currentPlayer = 0
		verify(gameState, times(1)).stackCards = anyList()
		verify(gameState, times(1)).centralCards = anyList()
		verify(playerMock, times(1)).handCards = anyList()
	}

	/**
	 * Tests if the score calculation handles the special case, for three different suits but the same values
	 */
	@Test
	fun `calculate score with three time the same value but different suit`() {
		val cards = listOf(
			Card(CardSuit.DIAMONDS, CardValue.ACE),
			Card(CardSuit.CLUBS, CardValue.ACE),
			Card(CardSuit.HEARTS, CardValue.ACE)
		)

		val result = gameService.calculateScore(cards)

		assertThat(result).isEqualTo(30.5)
	}

	/**
	 * Tests the score calculation method for a valid score (namely 31)
	 */
	@Test
	fun `calculate score all hearts to score 31`() {
		val cards = listOf(
			Card(CardSuit.HEARTS, CardValue.ACE),
			Card(CardSuit.HEARTS, CardValue.KING),
			Card(CardSuit.HEARTS, CardValue.QUEEN)
		)

		val result = gameService.calculateScore(cards)

		assertThat(result).isEqualTo(31.0)
	}

	/**
	 * Tests the score calculation for another valid value (25)
	 */
	@Test
	fun `calculate score all hearts to score 25`() {
		val cards = listOf(
			Card(CardSuit.HEARTS, CardValue.SEVEN),
			Card(CardSuit.HEARTS, CardValue.EIGHT),
			Card(CardSuit.HEARTS, CardValue.QUEEN)
		)

		val result = gameService.calculateScore(cards)

		assertThat(result).isEqualTo(25.0)
	}

	/**
	 * Private inner class to stub finish game, so we don't have to mock too much in some tests
	 */
	private class GameServiceStub(rootService: RootService): GameService(rootService) {
		override fun finishGame() {
			return
		}
	}
}