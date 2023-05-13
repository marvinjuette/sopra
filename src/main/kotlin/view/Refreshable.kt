package view

/**
 * Interface to make communication between UI and underlying services easier
 */
interface Refreshable {

	/**
	 * Default implementation that does nothing, so that not every controller has to implement this method
	 */
	fun refreshAfterGameStart() {
		return
	}

	/**
	 * Default implementation that does nothing, so that not every controller has to implement this method
	 */
	fun refreshAfterGameEnd() {
		return
	}

	/**
	 * Default implementation that does nothing, so that not every controller has to implement this method
	 */
	fun refreshAfterKnock() {
		return
	}

	/**
	 * Default implementation that does nothing, so that not every controller has to implement this method
	 */
	fun refreshAfterPass() {
		return
	}

	/**
	 * Default implementation that does nothing, so that not every controller has to implement this method
	 */
	fun refreshAfterPassWithCardsExchanged() {
		return
	}

	/**
	 * Default implementation that does nothing, so that not every controller has to implement this method
	 */
	fun refreshAfterChangedCentralCards() {
		return
	}

	/**
	 * Default implementation that does nothing, so that not every controller has to implement this method
	 */
	fun refreshAfterPlayerChange() {
		return
	}

	/**
	 * Default implementation that does nothing, so that not every controller has to implement this method
	 */
	fun refreshAfterPlayerRevealedCards() {
		return
	}

	/**
	 * Default implementation that does nothing, so that not every controller has to implement this method
	 */
	fun refreshAfterNewGame() {
		return
	}
}