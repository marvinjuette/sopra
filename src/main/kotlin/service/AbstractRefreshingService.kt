package service

import view.Refreshable

/**
 * Abstract class to manage the refreshables from the ui.
 *
 * @property refreshables [MutableList] of implementations of the [Refreshable]-Interface
 */
abstract class AbstractRefreshingService {

    private val refreshables = mutableListOf<Refreshable>()

    /**
     * Registers new refreshables.
     */
    fun addRefreshable(newRefreshable: Refreshable) {
        refreshables.add(newRefreshable)
    }

    /**
     * Calls a method on all known refreshables.
     */
    fun onAllRefreshables(method: Refreshable.() -> Unit) {
        refreshables.forEach { it.method() }
    }

}