package it.pierosilvestri.core.data.database

import android.content.Context
import androidx.lifecycle.MutableLiveData
import it.pierosilvestri.core.R
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

/**
 * This will be the singleton having the players records.
 * This class should be replaced by a database.
 */
class MockDatabase {
    private val players = MutableStateFlow<List<Player>>(emptyList())

    init {
        val jsonString = mockData
        val json = Json.parseToJsonElement(jsonString).jsonObject["players"]
        val playersDto = Json.decodeFromJsonElement<List<PlayerDto>>(json!!)
        for (playerDto in playersDto) {
            addPlayer(playerDto.toPlayer())
        }
    }

    fun getAllPlayers(): Flow<List<Player>> {
        return players
    }

    fun getPlayer(playerId: String): Player? {
        return players.value.find { it.id == playerId }
    }

    fun addPlayer(player: Player) {
        val currentList = players.value.toMutableList()
        currentList.add(player)
        players.value = currentList
    }

    fun addSession(session: Session, player: Player) {
        val sessions = mutableListOf<Session>()
        if (player.sessions != null) {
            sessions.addAll(player.sessions!!)
        }
        sessions.add(session)
        player.sessions = sessions.toList()
    }

    fun getSession(sessionId: String): Session? {
        for (player in players.value) {
            for (session in player.sessions!!) {
                if (session.id == sessionId) {
                    return session
                }
            }
        }
        return null
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
}