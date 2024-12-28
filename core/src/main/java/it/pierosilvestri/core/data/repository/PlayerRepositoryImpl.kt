package it.pierosilvestri.core.data.repository

import it.pierosilvestri.core.data.database.daos.LapDao
import it.pierosilvestri.core.data.database.daos.PlayerDao
import it.pierosilvestri.core.data.database.daos.PlayerPicturesDao
import it.pierosilvestri.core.data.database.daos.SessionDao
import it.pierosilvestri.core.data.database.model.LapEntity
import it.pierosilvestri.core.data.database.model.PlayerEntity
import it.pierosilvestri.core.data.database.model.PlayerPicturesEntity
import it.pierosilvestri.core.data.remote.RemotePlayerDataSource
import it.pierosilvestri.core.domain.DataError
import it.pierosilvestri.core.domain.Result
import it.pierosilvestri.core.domain.map
import it.pierosilvestri.core.domain.mapper.toLap
import it.pierosilvestri.core.domain.mapper.toLapEntity
import it.pierosilvestri.core.domain.mapper.toPlayer
import it.pierosilvestri.core.domain.mapper.toPlayerEntity
import it.pierosilvestri.core.domain.mapper.toPlayerPictures
import it.pierosilvestri.core.domain.mapper.toPlayerPicturesEntity
import it.pierosilvestri.core.domain.mapper.toSession
import it.pierosilvestri.core.domain.mapper.toSessionEntity
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.PlayerPictures
import it.pierosilvestri.core.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class PlayerRepositoryImpl(
    private val remotePlayerDataSource: RemotePlayerDataSource,
    private val playerDao: PlayerDao,
    private val sessionDao: SessionDao,
    private val lapDao: LapDao,
    private val playerPicturesDao: PlayerPicturesDao
) : PlayerRepository {

    override fun getPlayers(): Flow<List<Player>> {
        return flow {
            playerDao.getAll().collect { players ->
                val playersList = players.map { player ->
                    val sessionsEntity = sessionDao.getSessionsByPlayerId(player.id)
                    val sessions = sessionsEntity.map { session ->
                        val lapsEntity = lapDao.getLapsBySessionId(session.id)
                        val laps = lapsEntity.map { lap ->
                            lap.toLap()
                        }
                        session.toSession(laps)
                    }
                    val picturesEntity: PlayerPicturesEntity? =
                        playerPicturesDao.getPlayerPicturesByPlayerId(player.id)
                    val pictures: PlayerPictures? = picturesEntity?.toPlayerPictures()
                    player.toPlayer(sessions, pictures)
                }
                emit(playersList)
            }
        }
    }

    override suspend fun getPlayersFromRemote(): Result<List<Player>, DataError.Remote> {
        return remotePlayerDataSource
            .getPlayers()
            .map { dto ->
                dto.results.map { it.toPlayer() }
            }
    }

    override suspend fun getPlayer(playerId: String): Player? {
        val player = playerDao.getById(playerId);
        val sessions = sessionDao.getSessionsByPlayerId(playerId).map { session ->
            val lapsEntity = lapDao.getLapsBySessionId(session.id)
            val laps = lapsEntity.map { lap ->
                lap.toLap()
            }
            session.toSession(laps)
        }
        val picturesEntity = playerPicturesDao.getPlayerPicturesByPlayerId(playerId)
        val pictures = picturesEntity.toPlayerPictures()

        return player.toPlayer(
            sessions,
            pictures
        )
    }

    override suspend fun addPlayer(player: Player) {
        playerDao.insert(player.toPlayerEntity())
    }

    override suspend fun addPlayers(players: List<Player>) {
        val playersEntities = players.map {
            it.id = UUID.randomUUID().toString()
            it.toPlayerEntity()
        }
        playerDao.insertAll(playersEntities)
        players.forEach { player ->
            val playerPicturesEntity = player.pictures?.toPlayerPicturesEntity(player.id)
            if (playerPicturesEntity != null) {
                playerPicturesDao.upsert(playerPicturesEntity)
            }
        }
    }
}