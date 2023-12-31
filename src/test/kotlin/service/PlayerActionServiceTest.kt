package service

import entity.GameState
import entity.Player
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
import utils.TestUtils.getPlayerList
import view.Refreshable

/**
 * This class contains all unit tests for the [PlayerActionService]
 *
 * @property rootService Mock object of the [RootService]
 * @property gameState Mock object of the [GameState]
 * @property gameService Mock object of the [GameService]
 * @property playerActionService Instance of the [PlayerActionService]
 */
@ExtendWith(MockitoExtension::class)
class PlayerActionServiceTest {

	@Mock
	private lateinit var rootService: RootService
	@Mock
	private lateinit var gameState: GameState
	@Mock
	private lateinit var gameService: GameService
	@Mock
	private lateinit var refreshable: Refreshable

	private lateinit var playerActionService: PlayerActionService

	/**
	 * Runs before every test to ensure we have a fresh playerActionService and to avoid mocking some stuff
	 * in all other methods.
	 */
	@BeforeEach
	fun init() {
		playerActionService = spy(PlayerActionService(rootService))
		playerActionService.addRefreshable(refreshable)
		`when`(rootService.gameState).thenReturn(gameState)
	}

	/**
	 * Tests if knock marks the player taking the action is properly marked
	 */
	@Test
	fun `knock marks player and resets pass counter`() {
		val player: Player = mock()

		`when`(gameState.currentPlayer).thenReturn(0)
		`when`(gameState.players).thenReturn(listOf(player))
		`when`(rootService.gameService).thenReturn(gameService)
		playerActionService.knock()

		verify(player, times(1)).hasKnocked = true
		verify(gameState, times(1)).passCounter = 0
		verify(refreshable, times(1)).refreshAfterKnock()
	}

	/**
	 * Tests if pass does nothing if not all players have passed
	 */
	@Test
	fun `pass if not all players have passed should not do any thing`() {
		`when`(gameState.passCounter).thenReturn(1)
		`when`(gameState.players).thenReturn(getPlayerList())
		`when`(rootService.gameService).thenReturn(gameService)

		playerActionService.pass()

		verify(gameState, times(2)).passCounter
		verify(gameState, times(1)).passCounter = 2
		verify(gameService, times(1)).nextPlayer()
		verify(refreshable, times(1)).refreshAfterPass()
		verifyNoMoreInteractions(gameState)
	}

	/**
	 * Test if pass does call finish game of the [GameService] if there are not enough cards on the card stack to
	 * exchange the three central cards.
	 */
	@Test
	fun `pass if all players have passed but there arent enough cards on the card stack`() {
		`when`(gameState.passCounter).thenReturn(3)
		`when`(gameState.players).thenReturn(getPlayerList())

		playerActionService.pass()

		verify(refreshable, times(1)).refreshAfterGameEnd()
	}

	/**
	 * Tests if pass exchanges the central cards if all conditions for that are met.
	 */
	@Test
	fun `pass if all players have passed and there are enough card on the card stack`() {
		`when`(gameState.passCounter).thenReturn(3)
		`when`(gameState.players).thenReturn(getPlayerList())
		`when`(gameState.stackCards).thenReturn(getListOfCards(3))
		`when`(rootService.gameService).thenReturn(gameService)

		playerActionService.pass()

		verify(gameState, times(1)).centralCards = anyList()
		verify(refreshable, times(1)).refreshAfterPassWithCardsExchanged()
	}

	/**
	 * Tests if all hand cards of the player are not the central cards and vice versa.
	 */
	@Test
	fun `change all cards should swap all cards of player with all central cards`() {
		val handCards = getListOfCards(3)
		val centralCards = getListOfCards(3)
		val player = Player("Player 1", false, handCards)

		`when`(gameState.currentPlayer).thenReturn(0)
		`when`(gameState.players).thenReturn(listOf(player))
		`when`(gameState.centralCards).thenReturn(centralCards)
		`when`(rootService.gameService).thenReturn(gameService)

		playerActionService.changeAllCards()

		assertThat(player.handCards).isEqualTo(centralCards)
		verify(gameState, times(1)).centralCards = handCards
		verify(refreshable, times(1)).refreshAfterChangedCentralCards()
	}

	/**
	 * Tests if the swapped hand card is part of the central cards lists not and vice versa.
	 */
	@Test
	fun `change single handCard with single open central card`() {
		val handCards = getListOfCards(3)
		val newHandCards = handCards.toMutableList()
		val centralCards = getListOfCards(3)
		val newCentralCards = centralCards.toMutableList()

		val player = Player("Player 1", false, newHandCards)
		`when`(gameState.currentPlayer).thenReturn(0)
		`when`(gameState.players).thenReturn(listOf(player))
		`when`(gameState.centralCards).thenReturn(newCentralCards)
		`when`(rootService.gameService).thenReturn(gameService)

		playerActionService.changeCard(0, 0)

		assertThat(newHandCards).containsOnly(centralCards[0], handCards[1], handCards[2])
		assertThat(newCentralCards).containsOnly(handCards[0], centralCards[1], centralCards[2])
		verify(refreshable, times(1)).refreshAfterChangedCentralCards()
	}

	/**
	 * Tests whether the pass counter is really set to 0
	 */
	@Test
	fun `reset pass count should reset the pass counter of the game state to 0`() {
		playerActionService.resetPassCount()

		verify(gameState, times(1)).passCounter = 0
	}
}