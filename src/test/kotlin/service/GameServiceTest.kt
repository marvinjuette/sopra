package service

import entity.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import utils.TestUtils.getListOfCards
import view.Refreshable

/**
 * This class contains all unit tests for the [GameService]
 *
 * @property rootService Mock object of the [RootService]
 * @property gameState Mock object of the [GameState]
 * @property playerActionService Mock object of the [PlayerActionService]
 * @property refreshable Instance of [Refreshable] to test on all refreshables
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
	@Mock
	private lateinit var refreshable: Refreshable

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
		gameService = spy(GameService(rootService))
		gameService.addRefreshable(refreshable)

		doReturn(gameState).`when`(rootService).gameState
		doReturn(playerActionService).`when`(rootService).playerActionService

		val playerMock: Player = mock()

		doNothing().`when`(playerActionService).resetPassCount()
		`when`(gameState.stackCards).thenReturn(getListOfCards(32))
		`when`(gameState.players).thenReturn(listOf(playerMock))

		gameService.startNewGame(listOf("Player 1"))

		verify(playerActionService, times(1)).resetPassCount()
		verify(gameState, times(1)).currentPlayer = 0
		verify(gameState, times(1)).stackCards = anyList()
		verify(gameState, times(1)).centralCards = anyList()
		verify(playerMock, times(1)).handCards = anyList()
		verify(refreshable, times(1)).refreshAfterGameStart()
	}

	/**
	 * Tests if the game state is properly updated by the next player method and if the right method
	 * of the [Refreshable]s is called.
	 */
	@Test
	fun `test game state updated correctly player 1 switched to player 2 player 2 hasn't knocked`() {
		gameService = spy(GameService(rootService))
		gameService.addRefreshable(refreshable)

		val player1: Player = mock()
		val player2: Player = mock()
		`when`(rootService.gameState).thenReturn(gameState)
		`when`(gameState.currentPlayer).thenReturn(0)
		`when`(gameState.players).thenReturn(listOf(player1, player2))
		`when`(player1.hasKnocked).thenReturn(false)

		gameService.nextPlayer()

		verify(gameState, times(1)).currentPlayer = 1
		verify(refreshable, times(1)).refreshAfterPlayerChange()
	}

	/**
	 * Tests if the game state is properly updated by the next player method and if the right method
	 * of the [Refreshable]s is called after we reached a player who has knocked.
	 */
	@Test
	fun `test game state updated correctly player 1 switched to player 2 and player 2 has knocked`() {
		gameService = spy(GameService(rootService))
		gameService.addRefreshable(refreshable)

		val player1: Player = mock()
		val player2: Player = mock()
		`when`(rootService.gameState).thenReturn(gameState)
		`when`(gameState.currentPlayer).thenReturn(0)
		`when`(gameState.players).thenReturn(listOf(player1, player2))
		`when`(player1.hasKnocked).thenReturn(true)

		gameService.nextPlayer()

		verify(gameState, times(1)).currentPlayer = 1
		verify(refreshable, times(1)).refreshAfterGameEnd()
	}

	/**
	 * Tests if the game state is properly updated and the proper method of the [Refreshable]s is called by
	 * the next player method if we have to jump to the start of the players list.
	 */
	@Test
	fun `test game state updated correctly player 2 switched to player 1`() {
		gameService = spy(GameService(rootService))
		gameService.addRefreshable(refreshable)

		val player1: Player = mock()
		val player2: Player = mock()
		`when`(rootService.gameState).thenReturn(gameState)
		`when`(gameState.currentPlayer).thenReturn(2)
		`when`(gameState.players).thenReturn(listOf(player1, player2))
		`when`(player1.hasKnocked).thenReturn(false)
		doReturn(player1).`when`(gameService).getCurrentPlayer()

		gameService.nextPlayer()

		verify(gameState, times(1)).currentPlayer = 0
		verify(refreshable, times(1)).refreshAfterPlayerChange()
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
	 * Tests the score calculation method for a valid score (namely 20)
	 */
	@Test
	fun `calculate score all hearts to score 20`() {
		val cards = listOf(
			Card(CardSuit.HEARTS, CardValue.KING),
			Card(CardSuit.CLUBS, CardValue.KING),
			Card(CardSuit.HEARTS, CardValue.QUEEN)
		)

		val result = gameService.calculateScore(cards)

		assertThat(result).isEqualTo(20.0)
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
	 * Tests the score calculation for another valid value (15)
	 */
	@Test
	fun `calculate score of heart cards to score 15`() {
		val cards = listOf(
			Card(CardSuit.HEARTS, CardValue.SEVEN),
			Card(CardSuit.HEARTS, CardValue.EIGHT),
			Card(CardSuit.CLUBS, CardValue.QUEEN)
		)

		val result = gameService.calculateScore(cards)

		assertThat(result).isEqualTo(15.0)
	}
}