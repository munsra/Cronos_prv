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
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject

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

    fun getAllamPlayers(): Flow<List<Player>> {
        return players
    }

    fun addPlayer(player: Player) {
        val currentList = players.value.toMutableList()
        currentList.add(player)
        players.value = currentList
    }

    fun addPlayers(players: List<Player>) {
        for (player in players) {
            addPlayer(player)
        }
    }

    suspend fun addSession(session: Session, player: Player) {
        val sessions = mutableListOf<Session>()
        if (player.sessions != null) {
            sessions.addAll(player.sessions!!)
        }
        sessions.add(session)
        player.sessions = sessions.toList()
    }

    suspend fun addLap(lap: Lap, session: Session, player: Player) {
        val laps = mutableListOf<Lap>()
        if (session.laps != null) {
            laps.addAll(session.laps!!)
        }
        laps.add(lap)
        session.laps = laps.toList()
        addSession(session, player)
    }
}