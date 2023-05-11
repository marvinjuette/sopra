package service

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertNotNull
import entity.GameState

/**
 * This class contains all unit tests regarding the [RootService]
 *
 * @property rootService Instance of the [RootService]
 */
@ExtendWith(MockitoExtension::class)
class RootServiceTest {

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
}