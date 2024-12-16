package it.pierosilvestri.core.domain.export

import android.os.Environment
import it.pierosilvestri.core.domain.DataError
import it.pierosilvestri.core.domain.Result
import it.pierosilvestri.core.domain.mapper.toPlayerDto
import it.pierosilvestri.core.domain.model.Player
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream

fun exportPlayersToExcel(players: List<Player>): Result<String, DataError.Local> {
    val jsonPlayers = convertPlayersToJson(players)
    val excelFilePath = convertJsonToExcel(jsonPlayers, "players")
    if (excelFilePath == null) {
        return Result.Error(DataError.Local.PLAYER_TO_EXCET)
    }
    else
        return Result.Success(excelFilePath)
}

private fun convertPlayersToJson(players: List<Player>): JSONArray {
    val playersDto = players.map { it.toPlayerDto() }
    val jsonArray = playersDto.map { Json.parseToJsonElement(Json.encodeToString(it)) }
    val jsonObject = JSONArray(jsonArray)
    return jsonObject
}

private fun convertJsonToExcel(jsonArray: JSONArray, fileName: String): String? {
    try {
        // Create a new workbook and sheet
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Sheet1")

        // Write header row
        val headerRow = sheet.createRow(0)
        val keys = jsonArray.getJSONObject(0).keys().withIndex()
        keys.forEach {
            headerRow.createCell(it.index).setCellValue(it.value)
        }

        // Write data rows
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val row = sheet.createRow(i + 1)
            keys.forEach {
                row.createCell(it.index).setCellValue(jsonObject.optString(it.value))
            }
        }

        // Save the Excel file
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            "$fileName.xlsx"
        )
        val outputStream = FileOutputStream(file)
        workbook.write(outputStream)
        outputStream.close()
        workbook.close()

        return file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}