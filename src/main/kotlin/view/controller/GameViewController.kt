package view.controller

import extensions.onButtonClicked
import service.RootService
import tools.aqua.bgw.animation.MovementAnimation
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.dialog.Dialog
import tools.aqua.bgw.dialog.DialogType
import models.Colors
import models.Constants
import utils.CardViewGenerator.generateCardImage
import utils.CardViewGenerator.generateFlippedCardImage
import view.Refreshable
import view.SopraApplication
import view.scenes.GameScene
import java.util.NoSuchElementException

class GameViewController(
	private val gameScene: GameScene,
	private val rootService: RootService,
	private val sopraApplication: SopraApplication
): Refreshable {

	init {
		gameScene.changeCardButton.onButtonClicked { changeCard() }
		gameScene.changeAllCardsButton.onButtonClicked { rootService.playerActionService.changeAllCards() }
		gameScene.passButton.onButtonClicked { rootService.playerActionService.pass() }
		gameScene.knockButton.onButtonClicked { rootService.playerActionService.knock() }
	}

	override fun refreshAfterGameStart() {
		gameScene.passCounterLabel.text = "Pass: ${rootService.gameState.passCounter} / ${rootService.gameState.players.size}"

		refreshAfterChangedCentralCards()

		gameScene.centralStackCardStack.removeAll { true }
		rootService.gameState.stackCards.forEach { gameScene.centralStackCardStack.add(generateCardImage(it)) }

		refreshAfterPlayerChange()

		gameScene.knockButton.visual = Colors.RED
		gameScene.centralStackLabel.text = "Central Stack (${rootService.gameState.stackCards.size})"
	}

	override fun refreshAfterPlayerChange() {
		val player = rootService.gameState.players[rootService.gameState.currentPlayer]
		gameScene.playerNameLabel.text = player.name

		gameScene.handCardsLinearLayout.removeAll { true }
		player.handCards.forEach { gameScene.handCardsLinearLayout.add(generateCardImage(it)) }
	}

	override fun refreshAfterPass() {
		gameScene.passCounterLabel.text = "Pass: ${rootService.gameState.passCounter} / ${rootService.gameState.players.size}"
	}

	override fun refreshAfterPassWithCardsExchanged() {
		gameScene.centralStackLabel.text = "Central Stack (${rootService.gameState.stackCards.size})"
		gameScene.passCounterLabel.text = "Pass: ${rootService.gameState.passCounter} / ${rootService.gameState.players.size}"

		gameScene.centralCardsLinearLayout.removeAll { true }
		rootService.gameState.centralCards.forEach {
			gameScene.centralCardsLinearLayout.add(generateFlippedCardImage(it))
		}
		gameScene.centralCardsLinearLayout.forEach {
			cardView -> cardView.onMouseClicked = { focusCentralCard(cardView) }
		}
	}

	override fun refreshAfterPlayerRevealedCards() {
		gameScene.handCardsLinearLayout.forEach {cardView ->
			cardView.flip()
			cardView.onMouseClicked = { focusHandCard(cardView) }
		}

		val handCards = rootService.gameState.players[rootService.gameState.currentPlayer].handCards
		gameScene.pointsLabel.text = "Points: ${rootService.gameService.calculateScore(handCards)}"
	}

	private fun focusCentralCard(cardView: CardView) {
		val currentlyHighlightedCardView = gameScene.centralCardsLinearLayout.filter { !it.isFocusable }
		currentlyHighlightedCardView.forEach {
			moveAnimation(it, false)
			it.isFocusable = true
		}

		moveAnimation(cardView, true)
		cardView.isFocusable = false
	}


	private fun focusHandCard(cardView: CardView) {
		val currentlyHighlightedCardView = gameScene.handCardsLinearLayout.filter { !it.isFocusable }
		currentlyHighlightedCardView.forEach {
			moveAnimation(it, true)
			it.isFocusable = true
		}

		moveAnimation(cardView, false)
		cardView.isFocusable = false
	}

	private fun moveAnimation(cardView: CardView, moveDown: Boolean) {
		val yChange = 50 * if (moveDown) 1 else -1
		gameScene.playAnimation(
			MovementAnimation(
				componentView = cardView,
				byY = yChange,
				duration = 500
			).apply {
				onFinished = {
					cardView.posY += yChange
				}
			}
		)
	}

	override fun refreshAfterChangedCentralCards() {
		gameScene.centralCardsLinearLayout.removeAll { true }

		rootService.gameState.centralCards.forEach {
			gameScene.centralCardsLinearLayout.add(generateFlippedCardImage(it))
		}

		gameScene.centralCardsLinearLayout.forEach { cardView ->
			cardView.onMouseClicked = { focusCentralCard(cardView) }
		}
	}

	override fun refreshAfterKnock() {
		gameScene.knockButton.visual = Colors.CYAN
	}

	// Suppress swallowed exception because we catch it and show a custom error dialog with a custom error message
	@Suppress("SwallowedException")
	private fun changeCard() {
		val handIndex: Int
		val centralIndex: Int

		try {
			val selectedHandCard = gameScene.handCardsLinearLayout.first { !it.isFocusable }
			handIndex = gameScene.handCardsLinearLayout.indexOf(selectedHandCard)
		} catch (e: NoSuchElementException) {
			sopraApplication.showDialog(
				Dialog(
					DialogType.ERROR,
					Constants.NO_HAND_CARD_SELECTED_DIALOG_TITLE,
					Constants.NO_HAND_CARD_SELECTED_DIALOG_HEADER,
					Constants.NO_HAND_CARD_SELECTED_DIALOG_MESSAGE,
				)
			)
			return
		}

		try {
			val selectedCentralCard = gameScene.centralCardsLinearLayout.first { !it.isFocusable }
			centralIndex = gameScene.centralCardsLinearLayout.indexOf(selectedCentralCard)
		} catch (e: NoSuchElementException) {
			sopraApplication.showDialog(
				Dialog(
					DialogType.ERROR,
					Constants.NO_CENTRAL_CARD_SELECTED_DIALOG_TITLE,
					Constants.NO_CENTRAL_CARD_SELECTED_DIALOG_HEADER,
					Constants.NO_CENTRAL_CARD_SELECTED_DIALOG_MESSAGE
				)
			)
			return
		}

		rootService.playerActionService.changeCard(handIndex, centralIndex)
	}
}