package service

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertNotNull

@ExtendWith(MockitoExtension::class)
class RootServiceTest {

	private lateinit var rootService: RootService

	@BeforeEach
	fun init() {
		rootService = RootService()
	}

	@Test
	fun `check if player action service is initialized`() {
		assertNotNull(rootService.playerActionService)
	}

	@Test
	fun `check if game service is initialized`() {
		assertNotNull(rootService.gameService)
	}

	@Test
	fun `check if game state is initialized`() {
		assertNotNull(rootService.gameState)
	}

	@Test
	fun `check if start new game is called on game service`() {
		val gameServiceMock: GameService = mock()
		doNothing().`when`(gameServiceMock).startNewGame()

		val gameServiceField = rootService::class.java.getDeclaredField("gameService")
		gameServiceField.isAccessible = true
		gameServiceField.set(rootService, gameServiceMock)

		rootService.initializeGame()

		verify(gameServiceMock, times(1)).startNewGame()
	}
}