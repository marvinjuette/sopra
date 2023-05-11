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

	fun refreshAfterChangedCard() {
		return
	}

	fun refreshAfterChangedAllCards() {
		return
	}

	fun refreshAfterPlayerChange() {
		return
	}

	fun refreshAfterPlayerRevealedCards() {
		return
	}

}