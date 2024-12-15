package it.pierosilvestri.core.data.remote
import it.pierosilvestri.core.data.dto.PlayerResponseDto
import it.pierosilvestri.core.domain.DataError
import it.pierosilvestri.core.domain.Result

interface RemotePlayerDataSource {

    suspend fun getPlayers(): Result<PlayerResponseDto, DataError.Remote>

}