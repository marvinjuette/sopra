package view.controller

import entity.GameState
import entity.Player
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import service.GameService
import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import utils.TestUtils.getListOfCards
import utils.TestUtils.getPlayerList
import view.scenes.EndScene

/**
 * This class contains all unit tests to test functionality of the end view controller
 *
 * @property endScene Mock of the [EndScene]
 * @property rootService Mock of the [RootService]
 * @property gameState Mock of the [GameState]
 * @property gameService Mock of the [GameService]
 * @property endViewController Class undergoing the tests
 */
@ExtendWith(MockitoExtension::class)
class EndViewControllerTest {

	@Mock
	private lateinit var endScene: EndScene
	@Mock
	private lateinit var rootService: RootService
	@Mock
	private lateinit var gameState: GameState
	@Mock
	private lateinit var gameService: GameService

	private lateinit var endViewController: EndViewController

	/**
	 * Runs before each test to ensure that we have a fresh endViewController before each test is executed
	 */
	@BeforeEach
	fun init() {
		`when`(endScene.newGameButton).thenReturn(mock<Button>())
		`when`(endScene.quitButton).thenReturn(mock<Button>())
		`when`(endScene.newGameWithSamePlayerButton).thenReturn(mock<Button>())
		endViewController = EndViewController(endScene, rootService)
	}

	/**
	 * Test if start new game with same players correctly calls startNewGame in game service
	 */
	@Test
	fun `test if start new game with same players correctly calls startNewGame in game service`() {
		val players = getPlayerList()
		`when`(rootService.gameState).thenReturn(gameState)
		`when`(rootService.gameService).thenReturn(gameService)
		`when`(gameState.players).thenReturn(players)

		endViewController.startNewGameWithSamePlayers()

		verify(gameService, times(1)).startNewGame(players.map(Player::name))
	}

	/**
	 * Test refresh after game end with two players
	 */
	@Test
	fun `test refresh after game end with two players`() {
		val player1 = mock<Player> {
			on {name} doReturn "Player 1"
			on {handCards} doReturn getListOfCards(3)
		}
		val player2 = mock<Player> {
			on {name} doReturn "Player 2"
			on {handCards} doReturn getListOfCards(3)
		}

		val player1NameLabel: Label = mock()
		val player2NameLabel: Label = mock()
		`when`(rootService.gameState).thenReturn(gameState)
		`when`(rootService.gameService).thenReturn(gameService)
		`when`(gameState.players).thenReturn(listOf(player1, player2))
		`when`(gameService.calculateScore(any())).thenCallRealMethod()
		`when`(endScene.player1NameLabel).thenReturn(player1NameLabel)
		`when`(endScene.player1Score).thenReturn(mock())
		`when`(endScene.player2NameLabel).thenReturn(player2NameLabel)
		`when`(endScene.player2Score).thenReturn(mock())

		endViewController.refreshAfterGameEnd()

		verify(player1NameLabel, times(1)).text = "Player 1"
		verify(player2NameLabel, times(1)).text = "Player 2"
	}

	/**
	 * Test refresh after game end with three players
	 */
	@Test
	fun `test refresh after game end with three players`() {
		val player1 = mock<Player> {
			on {name} doReturn "Player 1"
			on {handCards} doReturn getListOfCards(3)
		}
		val player2 = mock<Player> {
			on {name} doReturn "Player 2"
			on {handCards} doReturn getListOfCards(3)
		}
		val player3 = mock<Player> {
			on {name} doReturn "Player 3"
			on {handCards} doReturn getListOfCards(3)
		}

		val player1NameLabel: Label = mock()
		val player2NameLabel: Label = mock()
		val player3NameLabel: Label = mock()
		`when`(rootService.gameState).thenReturn(gameState)
		`when`(rootService.gameService).thenReturn(gameService)
		`when`(gameState.players).thenReturn(listOf(player1, player2, player3))
		`when`(endScene.player1NameLabel).thenReturn(player1NameLabel)
		`when`(endScene.player1Score).thenReturn(mock())
		`when`(endScene.player2NameLabel).thenReturn(player2NameLabel)
		`when`(endScene.player2Score).thenReturn(mock())
		`when`(endScene.player3NameLabel).thenReturn(player3NameLabel)
		`when`(endScene.player3Score).thenReturn(mock())

		endViewController.refreshAfterGameEnd()

		verify(player1NameLabel, times(1)).text = "Player 1"
		verify(player2NameLabel, times(1)).text = "Player 2"
		verify(player3NameLabel, times(1)).text = "Player 3"
		verify(player3NameLabel, times(1)).isVisible = true
	}

	/**
	 * Test if name and score of player 3 and 4 are hidden after new game starts.
 	 */
	@Test
	fun `test if name and score of player 3 and 4 are hidden after new game starts`() {
		val player3NameLabel: Label = mock()
		val player3ScoreLabel: Label = mock()
		val player4NameLabel: Label = mock()
		val player4ScoreLabel: Label = mock()

		`when`(endScene.player3NameLabel).thenReturn(player3NameLabel)
		`when`(endScene.player3Score).thenReturn(player3ScoreLabel)
		`when`(endScene.player4NameLabel).thenReturn(player4NameLabel)
		`when`(endScene.player4Score).thenReturn(player4ScoreLabel)

		endViewController.refreshAfterGameStart()

		verify(player3NameLabel, times(1)).isVisible = false
		verify(player3ScoreLabel, times(1)).isVisible = false
		verify(player4NameLabel, times(1)).isVisible = false
		verify(player4ScoreLabel, times(1)).isVisible = false
	}

	/**
	 * Test refresh after game end with four players
	 */
	@Test
	fun `test refresh after game end with four players`() {
		val player1 = mock<Player> {
			on {name} doReturn "Player 1"
			on {handCards} doReturn getListOfCards(3)
		}
		val player2 = mock<Player> {
			on {name} doReturn "Player 2"
			on {handCards} doReturn getListOfCards(3)
		}
		val player3 = mock<Player> {
			on {name} doReturn "Player 3"
			on {handCards} doReturn getListOfCards(3)
		}
		val player4 = mock<Player> {
			on {name} doReturn "Player 4"
			on {handCards} doReturn getListOfCards(3)
		}

		val player1NameLabel: Label = mock()
		val player2NameLabel: Label = mock()
		val player3NameLabel: Label = mock()
		val player4NameLabel: Label = mock()
		`when`(rootService.gameState).thenReturn(gameState)
		`when`(rootService.gameService).thenReturn(gameService)
		`when`(gameState.players).thenReturn(listOf(player1, player2, player3, player4))
		`when`(endScene.player1NameLabel).thenReturn(player1NameLabel)
		`when`(endScene.player1Score).thenReturn(mock<Label>())
		`when`(endScene.player2NameLabel).thenReturn(player2NameLabel)
		`when`(endScene.player2Score).thenReturn(mock<Label>())
		`when`(endScene.player3NameLabel).thenReturn(player3NameLabel)
		`when`(endScene.player3Score).thenReturn(mock<Label>())
		`when`(endScene.player4NameLabel).thenReturn(player4NameLabel)
		`when`(endScene.player4Score).thenReturn(mock<Label>())

		endViewController.refreshAfterGameEnd()

		verify(player1NameLabel, times(1)).text = "Player 1"
		verify(player2NameLabel, times(1)).text = "Player 2"
		verify(player3NameLabel, times(1)).text = "Player 3"
		verify(player3NameLabel, times(1)).isVisible = true
		verify(player4NameLabel, times(1)).text = "Player 4"
		verify(player4NameLabel, times(1)).isVisible = true
	}
}