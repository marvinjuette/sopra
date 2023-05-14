package view.controller

import entity.GameState
import entity.Player
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import service.RootService
import tools.aqua.bgw.components.uicomponents.Label
import view.scenes.PlayerChangedScene

/**
 * This class contains all unit tests regarding the [PlayerChangedViewController]
 *
 * @property playerChangedScene Mock of the [PlayerChangedScene]
 * @property rootService Mock of the [RootService]
 * @property label Mock of a [Label]
 * @property playerChangedViewController The class undergoing the tests
 */
@ExtendWith(MockitoExtension::class)
class PlayerChangedViewControllerTest {

	@Mock
	private lateinit var playerChangedScene: PlayerChangedScene
	@Mock
	private lateinit var rootService: RootService
	@Mock
	private lateinit var label: Label

	private lateinit var playerChangedViewController: PlayerChangedViewController

	/**
	 * Runs before each test to ensure that we have a new and fresh [PlayerChangedViewController]
 	 */
	@BeforeEach
	fun init() {
		`when`(playerChangedScene.label).thenReturn(label)
		playerChangedViewController = PlayerChangedViewController(playerChangedScene, rootService)
	}

	/**
	 * Test generate reveal hint text contains player name.
	 */
	@Test
	fun `test generate reveal hint text contains player name`() {
		val playerName = "Spieler 1"
		val player: Player = mock {
			on {name} doReturn playerName
		}

		doReturn(mock<GameState> {
			on { currentPlayer } doReturn 0
			on { players } doReturn listOf(player)
		}).`when`(rootService).gameState

		val result = playerChangedViewController.generateRevealHintText()

		assertThat(result).contains(playerName)
	}

	/**
	 * Test if refresh after player change updates the hint text.
	 */
	@Test
	fun `test if refresh after player change updates the hint text`() {
		val playerChangedViewControllerSpy = spy(playerChangedViewController)
		doReturn("Moin.").`when`(playerChangedViewControllerSpy).generateRevealHintText()

		playerChangedViewControllerSpy.refreshAfterPlayerChange()

		verify(label, times(1)).text = "Moin."
	}

	/**
	 * Test if refresh after game start updates the hint text.
	 */
	@Test
	fun `test if refresh after game start updates the hint text`() {
		val playerChangedViewControllerSpy = spy(playerChangedViewController)
		doReturn("Moin.").`when`(playerChangedViewControllerSpy).generateRevealHintText()

		playerChangedViewControllerSpy.refreshAfterGameStart()

		verify(label, times(1)).text = "Moin."
	}
}