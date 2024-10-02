package com.invictus.bloghub.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class Api {

    private var _summary= mutableStateOf<String?>("LOADING ...")
    val summary: MutableState<String?> get() = _summary
    private val authToken = "Bearer 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"

    suspend fun fetchSummarizedText(content: String) {
        try {
            val url = URL("https://api-inference.huggingface.co/models/facebook/bart-large-cnn")  // Your API URL

            // Open connection
            val connection = withContext(Dispatchers.IO) {
                url.openConnection()
            } as HttpURLConnection
            connection.requestMethod = "POST"
            connection.doOutput = true
            connection.setRequestProperty("Authorization", authToken)
            connection.setRequestProperty("Content-Type", "application/json")

            val jsonInputString = """
                        {
                            "inputs": "$content"
                        }
                    """.trimIndent()

            OutputStreamWriter(connection.outputStream).use { writer ->
                writer.write(jsonInputString)
                writer.flush()
            }


            // Get response
            val responseCode = connection.responseCode
            val responseMessage = StringBuilder()

            BufferedReader(InputStreamReader(
                if (responseCode in 200..299) {
                    connection.inputStream
                } else {
                    connection.errorStream
                }
            )).use { reader ->
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    responseMessage.append(line)
                }
            }
            // Close the connection
            connection.disconnect()

            val jsonArray = JSONArray(responseMessage.toString())
            val jsonObject = jsonArray.getJSONObject(0)
            val summaryText = jsonObject.getString("summary_text")

            // Return the response
            _summary.value=summaryText.toString()
        } catch (e: Exception) {
            Log.d(TAG, "fetchSummarizedText: "+e.message)
            "Error: ${e.message}"
            _summary.value="FAILED TO SUMMARIZE"
    }

}}