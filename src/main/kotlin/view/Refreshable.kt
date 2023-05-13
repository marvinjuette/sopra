package view

interface Refreshable {

	fun refreshAfterGameStart() {
		return
	}

	fun refreshAfterGameEnd() {
		return
	}

	fun refreshAfterKnock() {
		return
	}

	fun refreshAfterPass() {
		return
	}

	fun refreshAfterPassWithCardsExchanged() {
		return
	}

	fun refreshAfterChangedCentralCards() {
		return
	}

	fun refreshAfterPlayerChange() {
		return
	}

	fun refreshAfterPlayerRevealedCards() {
		return
	}

	fun refreshAfterNewGame() {
		return
	}
}