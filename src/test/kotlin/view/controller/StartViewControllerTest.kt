package view.controller

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
import tools.aqua.bgw.components.uicomponents.TextField
import view.scenes.StartScene

/**
 * This class contains all unit tests regarding the [StartViewController]
 *
 * @property startScene Mock of the [StartScene]
 * @property rootService Mock of the [RootService]
 * @property startViewController Class undergoing the tests
 */
@ExtendWith(MockitoExtension::class)
class StartViewControllerTest {

	@Mock
	private lateinit var startScene: StartScene
	@Mock
	private lateinit var rootService: RootService

	private lateinit var startViewController: StartViewController

	/**
	 * Runs before each test to ensure that we have a new and fresh [StartViewController]
	 */
	@BeforeEach
	fun init() {
		`when`(startScene.startButton).thenReturn(mock())
		`when`(startScene.quitButton).thenReturn(mock())
		startViewController = StartViewController(startScene, rootService)
	}

	/**
	 * Show error label should make the error label visible.
	 */
	@Test
	fun `show error label should make the error label visible`() {
		val errorLabel: Label = mock()
		`when`(startScene.errorLabel).thenReturn(errorLabel)

		startViewController.showErrorLabel()

		verify(errorLabel, times(1)).isVisible = true
	}

	/**
	 * Test get player as name list with two players.
	 */
	@Test
	fun `test get player as name list with two players`() {
		val playerName1 = "Spieler 1"
		val playerName1TextField: TextField = mock {
			on { text }	doReturn playerName1
		}
		val playerName2 = "Spieler 2"
		val playerName2TextField: TextField = mock {
			on { text }	doReturn playerName2
		}
		val playerName3TextField: TextField = mock {
			on { text } doReturn ""
		}
		val playerName4TextField: TextField = mock {
			on { text } doReturn ""
		}

		`when`(startScene.playerName1).thenReturn(playerName1TextField)
		`when`(startScene.playerName2).thenReturn(playerName2TextField)
		`when`(startScene.playerName3).thenReturn(playerName3TextField)
		`when`(startScene.playerName4).thenReturn(playerName4TextField)

		val playerNames = startViewController.getPlayerNamesAsList()

		assertThat(playerNames).containsOnly(playerName1, playerName2)
	}

	/**
	 * Test get player as name list with three players.
	 */
	@Test
	fun `test get player as name list with three players`() {
		val playerName1 = "Spieler 1"
		val playerName1TextField: TextField = mock {
			on { text }	doReturn playerName1
		}
		val playerName2 = "Spieler 2"
		val playerName2TextField: TextField = mock {
			on { text }	doReturn playerName2
		}
		val playerName3 = "Spieler 3"
		val playerName3TextField: TextField = mock {
			on { text } doReturn playerName3
		}
		val playerName4TextField: TextField = mock {
			on { text } doReturn ""
		}

		`when`(startScene.playerName1).thenReturn(playerName1TextField)
		`when`(startScene.playerName2).thenReturn(playerName2TextField)
		`when`(startScene.playerName3).thenReturn(playerName3TextField)
		`when`(startScene.playerName4).thenReturn(playerName4TextField)

		val playerNames = startViewController.getPlayerNamesAsList()

		assertThat(playerNames).containsOnly(playerName1, playerName2, playerName3)
	}

	/**
	 * Test get player as name list with four players.
	 */
	@Test
	fun `test get player as name list with four players`() {
		val playerName1 = "Spieler 1"
		val playerName1TextField: TextField = mock {
			on { text }	doReturn playerName1
		}
		val playerName2 = "Spieler 2"
		val playerName2TextField: TextField = mock {
			on { text }	doReturn playerName2
		}
		val playerName3 = "Spieler 3"
		val playerName3TextField: TextField = mock {
			on { text } doReturn playerName3
		}
		val playerName4 = "Spieler 4"
		val playerName4TextField: TextField = mock {
			on { text } doReturn playerName4
		}

		`when`(startScene.playerName1).thenReturn(playerName1TextField)
		`when`(startScene.playerName2).thenReturn(playerName2TextField)
		`when`(startScene.playerName3).thenReturn(playerName3TextField)
		`when`(startScene.playerName4).thenReturn(playerName4TextField)

		val playerNames = startViewController.getPlayerNamesAsList()

		assertThat(playerNames).containsOnly(playerName1, playerName2, playerName3, playerName4)
	}
}