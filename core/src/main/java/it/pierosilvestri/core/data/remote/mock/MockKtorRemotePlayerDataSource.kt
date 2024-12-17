package it.pierosilvestri.core.data.remote.mock

import it.pierosilvestri.core.data.dto.PlayerDto
import it.pierosilvestri.core.data.dto.PlayerNameDto
import it.pierosilvestri.core.data.dto.PlayerResponseDto
import it.pierosilvestri.core.data.remote.RemotePlayerDataSource
import it.pierosilvestri.core.domain.DataError
import it.pierosilvestri.core.domain.Result

class MockKtorRemotePlayerDataSource: RemotePlayerDataSource {
    override suspend fun getPlayers(): Result<PlayerResponseDto, DataError.Remote> {
        val playerResponseDto = PlayerResponseDto(
            results = listOf(
                    PlayerDto(
                        id = "Player1",
                        fullname = PlayerNameDto(
                            title = "Mr",
                            first = "Mario",
                            last = "Rossi"
                        ),
                        picture = null,
                    ),
                PlayerDto(
                    id = "Player2",
                    fullname = PlayerNameDto(
                        title = "Ms",
                        first = "Lucia",
                        last = "Meleguzzi"
                    ),
                    picture = null,
                )
            )
        )

        return Result.Success(playerResponseDto)
    }

}