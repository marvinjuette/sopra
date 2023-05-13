package service

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertNotNull
import entity.GameState
import org.assertj.core.api.Assertions.assertThat
import org.mockito.Mock
import org.mockito.kotlin.anyOrNull
import view.Refreshable

/**
 * This class contains all unit tests regarding the [RootService]
 *
 * @property rootService Instance of the [RootService]
 */
@ExtendWith(MockitoExtension::class)
class RootServiceTest {

	@Mock
	private lateinit var refreshable: Refreshable

	private lateinit var rootService: RootService

	/**
	 * Runs before each test to ensure we have a fresh rootService at the beginning of each test
	 */
	@BeforeEach
	fun init() {
		rootService = RootService()
	}

	/**
	 * Tests if the [PlayerActionService] is initialized
	 */
	@Test
	fun `check if player action service is initialized`() {
		assertNotNull(rootService.playerActionService)
	}

	/**
	 * Tests if the [GameService] is initialized
	 */
	@Test
	fun `check if game service is initialized`() {
		assertNotNull(rootService.gameService)
	}

	/**
	 * Tests if the [GameState] is initialized
	 */
	@Test
	fun `check if game state is initialized`() {
		assertNotNull(rootService.gameState)
	}

	/**
	 * Tests if refreshable is added to both [GameService] and [PlayerActionService]
	 */
	@Test
	fun `check if the refreshable is added to game service and player action service`() {
		rootService.addRefreshable(refreshable)

		assertThat(rootService.gameService.refreshables).isNotEmpty
		assertThat(rootService.playerActionService.refreshables).isNotEmpty
	}

	/**
	 * Tests if there are no addRefreshable call if there is no refreshable
	 */
	@Test
	fun `tests if there are no addRefreshable call if there is no refreshable`() {
		val rootServiceSpy = spy(rootService)
		rootServiceSpy.addRefreshables()

		verify(rootServiceSpy, never()).addRefreshable(anyOrNull())
	}

	/**
	 * Tests if there is one addRefreshable call if there is one refreshable
	 */
	@Test
	fun `tests if there is one addRefreshable call if there is one refreshable`() {
		val rootServiceSpy = spy(rootService)
		rootServiceSpy.addRefreshables(refreshable)

		verify(rootServiceSpy, times(1)).addRefreshable(refreshable)
	}

	/**
	 * Tests if there are two addRefreshable call if there are two refreshable
	 */
	@Test
	fun `tests if there are two addRefreshable call if there are two refreshable`() {
		val rootServiceSpy = spy(rootService)
		rootServiceSpy.addRefreshables(refreshable, refreshable)

		verify(rootServiceSpy, times(2)).addRefreshable(refreshable)
	}
}