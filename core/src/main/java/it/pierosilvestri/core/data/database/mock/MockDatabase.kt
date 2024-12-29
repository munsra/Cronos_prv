package it.pierosilvestri.core.data.database.mock

import it.pierosilvestri.core.data.dto.PlayerDto
import it.pierosilvestri.core.domain.mapper.toPlayer
import it.pierosilvestri.core.domain.model.Lap
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.Session
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * This will be the singleton having the players records.
 * This class should be replaced by a database.
 */
class MockDatabase {
    private val players = MutableStateFlow<List<Player>>(emptyList())

    @OptIn(ExperimentalUuidApi::class)
    fun loadPlayersFromMockData() {
        val jsonString = mockData
        val json = Json { ignoreUnknownKeys = true }
        val jsonObject = json.parseToJsonElement(jsonString).jsonObject["results"]
        val playersDto = json.decodeFromJsonElement<List<PlayerDto>>(jsonObject!!)
        for (playerDto in playersDto) {
            playerDto.id = Uuid.random().toString()
            addPlayer(playerDto.toPlayer())
        }
    }

    fun getAllPlayers(): Flow<List<Player>> {
        return players
    }

    fun addPlayer(player: Player) {
        val currentList = players.value.toMutableList()
        currentList.add(player)
        players.value = currentList
    }

    fun addPlayers(newPlayerList: List<Player>) {
        players.value += newPlayerList
    }

    fun addSession(newSession: Session, player: Player) {
        val sessions = mutableListOf<Session>()
        if (player.sessions != null) {
            sessions.addAll(player.sessions!!)
        }
        // If the session already exists, remove it from the list
        // because I'm going to add it again with the new data.
        for (session in sessions){
            if(session.id == newSession.id){
                sessions.remove(session)
            }
        }
        sessions.add(newSession)
        player.sessions = sessions.toList()
    }

    fun deleteSession(session: Session) {
        for (player in players.value) {
            if (player.sessions != null) {
                for (playerSession in player.sessions!!) {
                    if (playerSession.id == session.id) {
                        val sessionMutableList = player.sessions!!.toMutableList()
                        sessionMutableList.remove(session)
                        player.sessions = sessionMutableList
                    }
                }
            }
        }
    }

    fun addLap(lap: Lap, session: Session, player: Player) {
        val laps = mutableListOf<Lap>()
        if (session.laps != null) {
            laps.addAll(session.laps!!)
        }
        laps.add(lap)
        session.laps = laps.toList()
        addSession(session, player)
    }

    fun getPlayer(playerId: String): Player? {
        return players.value.find { it.id == playerId }
    }

    fun getSession(sessionId: String): Session? {
        for (player in players.value) {
            if(player.sessions != null){
                for (session in player.sessions!!) {
                    if (session.id == sessionId) {
                        return session
                    }
                }
            }
        }
        return null
    }
}