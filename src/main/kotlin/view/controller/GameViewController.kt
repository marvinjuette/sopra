package view.controller

import extensions.onButtonClicked
import service.RootService
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.dialog.Dialog
import tools.aqua.bgw.dialog.DialogType
import view.*
import view.utils.CardViewGenerator.generateCardImage
import view.utils.CardViewGenerator.generateFlippedCardImage
import view.animations.SelectCardAnimation.moveAnimation
import view.models.Colors.CYAN
import view.models.Colors.RED
import view.models.Constants.NO_CENTRAL_CARD_SELECTED_DIALOG_HEADER
import view.models.Constants.NO_CENTRAL_CARD_SELECTED_DIALOG_MESSAGE
import view.models.Constants.NO_CENTRAL_CARD_SELECTED_DIALOG_TITLE
import view.models.Constants.NO_HAND_CARD_SELECTED_DIALOG_HEADER
import view.models.Constants.NO_HAND_CARD_SELECTED_DIALOG_MESSAGE
import view.models.Constants.NO_HAND_CARD_SELECTED_DIALOG_TITLE
import view.scenes.GameScene
import java.util.NoSuchElementException

/**
 * This is the controller class for the end scene
 *
 * @param gameScene References the [GameScene] that is to be controled by this controller
 * @param rootService References the [RootService] to access things like the [entity.GameState]
 * @param sopraApplication References the [SopraApplication] to use it to show dialogs
 */
class GameViewController(
	private val gameScene: GameScene,
	private val rootService: RootService,
	private val sopraApplication: SopraApplication
): Refreshable {

	/**
	 * Initialize some scene elements with their base functionality
	 */
	init {
		gameScene.changeCardButton.onButtonClicked { changeCard() }
		gameScene.changeAllCardsButton.onButtonClicked { rootService.playerActionService.changeAllCards() }
		gameScene.passButton.onButtonClicked { rootService.playerActionService.pass() }
		gameScene.knockButton.onButtonClicked { rootService.playerActionService.knock() }
	}

	/**
	 * Updates all ui elements to display their initial values
	 */
	override fun refreshAfterGameStart() {
		refreshAfterPass()
		refreshAfterChangedCentralCards()

		gameScene.centralStackCardStack.removeAll { true }
		rootService.gameState.stackCards.forEach { gameScene.centralStackCardStack.add(generateCardImage(it)) }
		gameScene.centralStackLabel.text = "Central Stack (${rootService.gameState.stackCards.size})"

		refreshAfterPlayerChange()

		gameScene.knockButton.visual = RED
	}

	/**
	 * Updates the displayed player name and creates the (hidden) card views into the player hand cards linear layout.
	 */
	override fun refreshAfterPlayerChange() {
		val player = rootService.gameState.players[rootService.gameState.currentPlayer]
		gameScene.playerNameLabel.text = player.name

		gameScene.handCardsLinearLayout.removeAll { true }
		player.handCards.forEach { gameScene.handCardsLinearLayout.add(generateCardImage(it)) }
	}

	/**
	 *  Updates the pass counter after a player has passed.
	 */
	override fun refreshAfterPass() {
		gameScene.passCounterLabel.text = "Pass: ${rootService.gameState.passCounter} / ${rootService.gameState.players.size}"
	}

	/**
	 * Updates the pass counter and replaces the central cards with the 3 new central cards
	 */
	override fun refreshAfterPassWithCardsExchanged() {
		refreshAfterPass()
		gameScene.centralStackLabel.text = "Central Stack (${rootService.gameState.stackCards.size})"

		gameScene.centralCardsLinearLayout.removeAll { true }
		rootService.gameState.centralCards.forEach {
			gameScene.centralCardsLinearLayout.add(generateFlippedCardImage(it))
		}
		gameScene.centralCardsLinearLayout.forEach {
			cardView -> cardView.onMouseClicked = { focusCentralCard(cardView) }
		}
	}

	/**
	 * Flips the hand cards of the player to make them visible and calculated the player score,
	 * after the player revealed them.
	 */
	override fun refreshAfterPlayerRevealedCards() {
		gameScene.handCardsLinearLayout.forEach {cardView ->
			cardView.flip()
			cardView.onMouseClicked = { focusHandCard(cardView) }
		}

		val handCards = rootService.gameState.players[rootService.gameState.currentPlayer].handCards
		gameScene.pointsLabel.text = "Points: ${rootService.gameService.calculateScore(handCards)}"
	}

	/**
	 * Helper function to un-focus the previous focused (central) card (if available) and focuses the newly selected.
	 *
	 * @param cardView References the [CardView] which is to be focused
	 */
	internal fun focusCentralCard(cardView: CardView) {
		if (!cardView.isFocusable) {
			return
		}

		val currentlyHighlightedCardView = gameScene.centralCardsLinearLayout.filter { !it.isFocusable }
		currentlyHighlightedCardView.forEach {
			moveAnimation(it, false, gameScene)
			it.isFocusable = true
		}

		moveAnimation(cardView, true, gameScene)
		cardView.isFocusable = false
	}


	/**
	 * Helper function to un-focus the previous focused (hand) card (if available) and focuses the newly selected
	 *
	 * @param cardView References the [CardView] which is to be focused
	 */
	internal fun focusHandCard(cardView: CardView) {
		if (!cardView.isFocusable) {
			return
		}

		val currentlyHighlightedCardView = gameScene.handCardsLinearLayout.filter { !it.isFocusable }
		currentlyHighlightedCardView.forEach {
			moveAnimation(it, true, gameScene)
			it.isFocusable = true
		}

		moveAnimation(cardView, false, gameScene)
		cardView.isFocusable = false
	}

	/**
	 *  Updates the central cards after a player has changed one or all his hand cards with one
	 *  or all of the central cards.
	 */
	override fun refreshAfterChangedCentralCards() {
		gameScene.centralCardsLinearLayout.removeAll { true }

		rootService.gameState.centralCards.forEach {
			gameScene.centralCardsLinearLayout.add(generateFlippedCardImage(it))
		}

		gameScene.centralCardsLinearLayout.forEach { cardView ->
			cardView.onMouseClicked = { focusCentralCard(cardView) }
		}
	}

	/**
	 * Changes the color of the knock button after a player knocked to indicate to every player that it is their last
	 * round.
	 */
	override fun refreshAfterKnock() {
		gameScene.knockButton.visual = CYAN
	}

	/**
	 * This method checks whether one hand and one central card is selected if both are selected swaps them
	 */
	// SwallowedException because we catch the exception and show an error dialog with a custom error message
	@Suppress("SwallowedException")
	internal fun changeCard() {
		val handIndex: Int
		val centralIndex: Int

		try {
			val selectedHandCard = gameScene.handCardsLinearLayout.first { !it.isFocusable }
			handIndex = gameScene.handCardsLinearLayout.indexOf(selectedHandCard)
		} catch (e: NoSuchElementException) {
			sopraApplication.showDialog(
				Dialog(
					DialogType.ERROR,
					NO_HAND_CARD_SELECTED_DIALOG_TITLE,
					NO_HAND_CARD_SELECTED_DIALOG_HEADER,
					NO_HAND_CARD_SELECTED_DIALOG_MESSAGE,
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
					NO_CENTRAL_CARD_SELECTED_DIALOG_TITLE,
					NO_CENTRAL_CARD_SELECTED_DIALOG_HEADER,
					NO_CENTRAL_CARD_SELECTED_DIALOG_MESSAGE
				)
			)
			return
		}

		rootService.playerActionService.changeCard(handIndex, centralIndex)
	}
}