package it.pierosilvestri.core.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import it.pierosilvestri.core.domain.DataError
import it.pierosilvestri.core.domain.Result
import it.pierosilvestri.core.data.dto.PlayerResponseDto
import it.pierosilvestri.core.data.network.safeCall

private const val BASE_URL = "https://randomuser.me/api"

class KtorRemotePlayerDataSource(
    private val httpClient: HttpClient
): RemotePlayerDataSource {

    override suspend fun getPlayers(): Result<PlayerResponseDto, DataError.Remote> {
        return safeCall<PlayerResponseDto> {
            httpClient.get(
                urlString = "$BASE_URL/?seed=empatica&inc=name,picture&gender=male&results=10&noinfo"
            )
        }
    }

}