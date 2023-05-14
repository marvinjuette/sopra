package view.controller

import entity.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import service.GameService
import service.PlayerActionService
import service.RootService
import tools.aqua.bgw.components.container.CardStack
import tools.aqua.bgw.components.container.LinearLayout
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.dialog.DialogType
import tools.aqua.bgw.visual.Visual
import utils.TestUtils.getListOfCards
import utils.TestUtils.getPlayerList
import view.SopraApplication
import view.models.Colors.CYAN
import view.models.Colors.RED
import view.models.Constants.NO_CENTRAL_CARD_SELECTED_DIALOG_MESSAGE
import view.models.Constants.NO_HAND_CARD_SELECTED_DIALOG_MESSAGE
import view.scenes.GameScene

/**
 * This class contains all unit tests regarding the [GameViewController]
 *
 * @property gameScene Mock of the [GameScene]
 * @property rootService Mock of the [RootService]
 * @property sopraApplication Mock of the [SopraApplication]
 * @property gameViewController Class undergoing the tests
 */
@ExtendWith(MockitoExtension::class)
class GameViewControllerTest {

	@Mock
	private lateinit var gameScene: GameScene
	@Mock
	private lateinit var rootService: RootService
	@Mock
	private lateinit var sopraApplication: SopraApplication

	private lateinit var gameViewController: GameViewController

	/**
	 * Runs before each test to ensure that we have a new and fresh gameViewController after each test
	 */
	@BeforeEach
	fun init() {
		`when`(gameScene.changeCardButton).thenReturn(mock())
		`when`(gameScene.changeAllCardsButton).thenReturn(mock())
		`when`(gameScene.passButton).thenReturn(mock())
		`when`(gameScene.knockButton).thenReturn(mock())
		gameViewController = GameViewController(gameScene, rootService, sopraApplication)
	}

	/**
	 * Test if refresh after game start displays every important information.
	 */
	@Test
	fun `test if refresh after game start displays every important information`() {
		val gameViewControllerSpy = spy(gameViewController)

		val gameState: GameState = mock {
			on { stackCards } doReturn getListOfCards(23)
		}
		val centralStackMock: CardStack<CardView> = spy(CardStack())
		val knockButtonMock: Button = mock()
		val centralStackLabelMock: Label = mock()

		`when`(gameScene.knockButton).thenReturn(knockButtonMock)
		`when`(gameScene.centralStackLabel).thenReturn(centralStackLabelMock)
		doReturn(centralStackMock).`when`(gameScene).centralStackCardStack
		doReturn(gameState).`when`(rootService).gameState
		doNothing().`when`(gameViewControllerSpy).refreshAfterPass()
		doNothing().`when`(gameViewControllerSpy).refreshAfterChangedCentralCards()
		doNothing().`when`(gameViewControllerSpy).refreshAfterPlayerChange()

		gameViewControllerSpy.refreshAfterGameStart()

		verify(gameViewControllerSpy, times(1)).refreshAfterPass()
		verify(gameViewControllerSpy, times(1)).refreshAfterChangedCentralCards()
		verify(centralStackMock, times(23)).add(any(), anyInt())
		verify(centralStackLabelMock, times(1)).text = check {
			assertThat(it).contains("23")
		}
		verify(gameViewControllerSpy, times(1)).refreshAfterPlayerChange()
		verify(knockButtonMock, times(1)).visual = RED
	}

	/**
	 * Test if after player change adds the hand cards in a hidden manner.
	 */
	@Test
	fun `test if after player change adds the hand cards in a hidden manner`() {
		val player: Player = mock {
			on { name } doReturn "Player 1"
			on { handCards } doReturn getListOfCards(3)
		}
		val gameState: GameState = mock {
			on { currentPlayer } doReturn 0
			on { players } doReturn listOf(player)
		}
		val playerNameLabelMock: Label = mock()
		val handCardsLinearLayoutMock = spy(LinearLayout<CardView>())
		`when`(gameScene.playerNameLabel).thenReturn(playerNameLabelMock)
		`when`(gameScene.handCardsLinearLayout).thenReturn(handCardsLinearLayoutMock)
		doReturn(gameState).`when`(rootService).gameState

		gameViewController.refreshAfterPlayerChange()

		verify(playerNameLabelMock, times(1)).text = player.name
		verify(handCardsLinearLayoutMock, times(3)).add(any(), anyInt())
	}

	/**
	 * Test if refresh after pass updates the pass counter label.
	 */
	@Test
	fun `test if refresh after pass updates the pass counter label`() {
		val passCounterLabel: Label = mock()
		val gameState: GameState = mock {
			on { passCounter } doReturn 1
			on { players } doReturn getPlayerList()
		}

		`when`(gameScene.passCounterLabel).thenReturn(passCounterLabel)
		doReturn(gameState).`when`(rootService).gameState

		gameViewController.refreshAfterPass()

		verify(passCounterLabel, times(1)).text = check {
			assertThat(it).contains("1 / 3")
		}
	}

	/**
	 * Test if refresh after pass with cards exchanged the label and the central cards are updated.
	 */
	@Test
	fun `test if refresh after pass with cards exchanged the label and the central cards are updated`() {
		val gameViewControllerSpy = spy(gameViewController)
		val centralStackLabelMock: Label = mock()
		val gameState: GameState = mock {
			on { stackCards } doReturn getListOfCards(20)
			on { centralCards } doReturn getListOfCards(3)
		}
		val centralCardsLinearLayoutMock = spy(LinearLayout<CardView>())

		`when`(gameScene.centralStackLabel).thenReturn(centralStackLabelMock)
		`when`(gameScene.centralCardsLinearLayout).thenReturn(centralCardsLinearLayoutMock)
		doReturn(gameState).`when`(rootService).gameState
		doNothing().`when`(gameViewControllerSpy).refreshAfterPass()

		gameViewControllerSpy.refreshAfterPassWithCardsExchanged()

		verify(gameViewControllerSpy, times(1)).refreshAfterPass()
		verify(centralStackLabelMock, times(1)).text = check {
			assertThat(it).contains("20")
		}
		verify(centralCardsLinearLayoutMock, times(3)).add(any(), anyInt())
		assertThat(centralCardsLinearLayoutMock).allMatch { it.currentSide == CardView.CardSide.FRONT }
	}

	/**
	 * Test if after a player reveals its card all cards are revealed and points are displayed.
	 */
	@Test
	fun `test if after a player reveals its card all cards are revealed and points are displayed`() {
		val player: Player = mock {
			on { handCards } doReturn getListOfCards(3)
		}
		val handCardsLinearLayoutMock = spy(LinearLayout<CardView>())
		val pointsLabelMock: Label = mock()
		val gameState: GameState = mock {
			on { currentPlayer } doReturn 0
			on { players } doReturn listOf(player)
		}
		val gameServiceMock: GameService = mock()

		`when`(gameScene.handCardsLinearLayout).thenReturn(handCardsLinearLayoutMock)
		`when`(gameScene.pointsLabel).thenReturn(pointsLabelMock)
		`when`(rootService.gameService).thenReturn(gameServiceMock)
		doReturn(21.0).`when`(gameServiceMock).calculateScore(any())
		doReturn(gameState).`when`(rootService).gameState

		gameViewController.refreshAfterPlayerRevealedCards()

		assertThat(handCardsLinearLayoutMock).allMatch { it.currentSide == CardView.CardSide.FRONT }
		verify(pointsLabelMock, times(1)).text = check {
			assertThat(it).contains("21")
		}
	}

	/**
	 * Test if already focused central card is not focusable.
	 */
	@Test
	fun `test if already focused central card is not focusable`() {
		val cardView: CardView = mock {
			on { isFocusable } doReturn false
		}

		gameViewController.focusCentralCard(cardView)

		verify(cardView, times(1)).isFocusable
		verifyNoMoreInteractions(cardView)
	}

	/**
	 * Test if currently focused central card is un-focused and newly selected card is focused.
	 */
	@Test
	fun `test if currently focused central card is un-focused and newly selected card is focused`() {
		val cardView1 = CardView(0, 0, 250, 150, Visual.EMPTY, Visual.EMPTY)
		val cardView2 = CardView(0, 0, 250, 150, Visual.EMPTY, Visual.EMPTY)
		cardView1.isFocusable = false
		cardView2.isFocusable = true

		val linearLayout = LinearLayout<CardView>()
		linearLayout.addAll(cardView1, cardView2)

		`when`(gameScene.centralCardsLinearLayout).thenReturn(linearLayout)

		gameViewController.focusCentralCard(cardView2)

		assertThat(cardView1.isFocusable).isTrue()
		assertThat(cardView2.isFocusable).isFalse()
	}

	/**
	 * Test if already focused hand card is not focusable.
	 */
	@Test
	fun `test if already focused hand card is not focusable`() {
		val cardView: CardView = mock {
			on { isFocusable } doReturn false
		}

		gameViewController.focusHandCard(cardView)

		verify(cardView, times(1)).isFocusable
		verifyNoMoreInteractions(cardView)
	}

	/**
	 * Test if currently focused hand card is un-focused and newly selected card is focused.
	 */
	@Test
	fun `test if currently focused hand card is un-focused and newly selected card is focused`() {
		val cardView1 = CardView(0, 0, 250, 150, Visual.EMPTY, Visual.EMPTY)
		val cardView2 = CardView(0, 0, 250, 150, Visual.EMPTY, Visual.EMPTY)
		cardView1.isFocusable = false
		cardView2.isFocusable = true

		val linearLayout = LinearLayout<CardView>()
		linearLayout.addAll(cardView1, cardView2)

		`when`(gameScene.centralCardsLinearLayout).thenReturn(linearLayout)

		gameViewController.focusCentralCard(cardView2)

		assertThat(cardView1.isFocusable).isTrue()
		assertThat(cardView2.isFocusable).isFalse()
	}

	/**
	 * Test if refresh after changed central cards updates the central cards.
	 */
	@Test
	fun `test if refresh after changed central cards updates the central cards`() {
		val newCentralCards = getListOfCards(3)
		val gameState: GameState = mock {
			on { centralCards } doReturn newCentralCards
		}
		val centralCardsLinearLayoutMock = spy(LinearLayout<CardView>())
		`when`(gameScene.centralCardsLinearLayout).thenReturn(centralCardsLinearLayoutMock)
		doReturn(gameState).`when`(rootService).gameState

		gameViewController.refreshAfterChangedCentralCards()

		verify(centralCardsLinearLayoutMock, times(3)).add(any(), anyInt())
	}

	/**
	 * Test if refresh after knock changes the color of the knock button.
	 */
	@Test
	fun `test if refresh after knock changes the color of the knock button`() {
		val knockButtonMock: Button = mock()
		`when`(gameScene.knockButton).thenReturn(knockButtonMock)

		gameViewController.refreshAfterKnock()

		verify(knockButtonMock, times(1)).visual = CYAN
	}

	/**
	 * Test change card but no hand card is selected.
	 */
	@Test
	fun `test change card but no hand card is selected`() {
		val cardView1 = CardView(0, 0, 250, 150, Visual.EMPTY, Visual.EMPTY)
		cardView1.isFocusable = true

		val linearLayout = LinearLayout<CardView>()
		linearLayout.add(cardView1)
		`when`(gameScene.handCardsLinearLayout).thenReturn(linearLayout)

		gameViewController.changeCard()

		verify(sopraApplication, times(1)).showDialog(check {
			assertThat(it.dialogType).isEqualTo(DialogType.ERROR)
			assertThat(it.message).isEqualTo(NO_HAND_CARD_SELECTED_DIALOG_MESSAGE)
		})
		verifyNoMoreInteractions(gameScene)
	}

	/**
	 * Test change card but no central card is selected.
	 */
	@Test
	fun `test change card but no central card is selected`() {
		val cardView1 = CardView(0, 0, 250, 150, Visual.EMPTY, Visual.EMPTY)
		cardView1.isFocusable = false
		val cardView2 = CardView(0, 0, 250, 150, Visual.EMPTY, Visual.EMPTY)
		cardView2.isFocusable = true

		val handLinearLayout = LinearLayout<CardView>()
		handLinearLayout.add(cardView1)
		val centralLinearLayout = LinearLayout<CardView>()
		centralLinearLayout.add(cardView2)

		`when`(gameScene.handCardsLinearLayout).thenReturn(handLinearLayout)
		`when`(gameScene.centralCardsLinearLayout).thenReturn(centralLinearLayout)


		gameViewController.changeCard()

		verify(sopraApplication, times(1)).showDialog(check {
			assertThat(it.dialogType).isEqualTo(DialogType.ERROR)
			assertThat(it.message).isEqualTo(NO_CENTRAL_CARD_SELECTED_DIALOG_MESSAGE)
		})
		verifyNoMoreInteractions(gameScene)
	}

	/**
	 * Test change card and both hand and central card is selected.
	 */
	@Test
	fun `test change card and both hand and central card is selected`() {
		val cardView1 = CardView(0, 0, 250, 150, Visual.EMPTY, Visual.EMPTY)
		cardView1.isFocusable = false
		val cardView2 = CardView(0, 0, 250, 150, Visual.EMPTY, Visual.EMPTY)
		cardView2.isFocusable = false

		val handLinearLayout = LinearLayout<CardView>()
		handLinearLayout.add(cardView1)
		val centralLinearLayout = LinearLayout<CardView>()
		centralLinearLayout.add(cardView2)

		`when`(gameScene.handCardsLinearLayout).thenReturn(handLinearLayout)
		`when`(gameScene.centralCardsLinearLayout).thenReturn(centralLinearLayout)

		val playerActionService: PlayerActionService = mock()
		doReturn(playerActionService).`when`(rootService).playerActionService


		gameViewController.changeCard()

		verify(sopraApplication, never()).showDialog(any())
		verify(playerActionService, times(1)).changeCard(0, 0)
	}
}