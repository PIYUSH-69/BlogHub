package com.invictus.bloghub.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class FlaskApi  {

    data class CategoryInfo(
        val name: String,
        val percentage: Double
    )

    private var _category= mutableStateOf<CategoryInfo?>(null)
    val category: MutableState<CategoryInfo?> get() = _category

    suspend fun fetchcategory(content: String) {
        try {
            val url = URL("http://192.168.0.106:5000/predict")  // Your API URL

            // Open connection
            val connection = withContext(Dispatchers.IO) {
                url.openConnection()
            } as HttpURLConnection
            connection.requestMethod = "POST"
            connection.doOutput = true
            connection.setRequestProperty("Content-Type", "application/json")

            val jsonInputString = """
                        {
                            "text": "$content"
                        }
                    """.trimIndent()

            OutputStreamWriter(connection.outputStream).use { writer ->
                writer.write(jsonInputString)
                writer.flush()
            }


            // Get response
            val responseCode = connection.responseCode
            val responseMessage = StringBuilder()
            Log.d(TAG, "fetchcategory: "+responseCode)
            Log.d(TAG, "fetchcategory: "+responseMessage)


            BufferedReader(
                InputStreamReader(
                if (responseCode in 200..299) {
                    connection.inputStream
                } else {
                    connection.errorStream
                }
            )
            ).use { reader ->
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    responseMessage.append(line)
                }
            }
            // Close the connection
            connection.disconnect()

            val responseJson = JSONObject(responseMessage.toString())
            val outputArray = responseJson.getJSONArray("output")

            // If you want to access the first set of values in the `output` array
            val firstOutput = outputArray.getJSONArray(0)

            // Extract the values (if needed, loop through them)
            val firstOutputList = mutableListOf<Double>()
            for (i in 0 until firstOutput.length()) {
                firstOutputList.add(firstOutput.getDouble(i))
            }

            // Find the maximum value using maxOrNull()
            val maxValue = firstOutputList.maxOrNull()
            val maxIndex = firstOutputList.indexOf(maxValue)
            val percentMaxValue = maxValue?.times(100)

            println("Maximum value: $percentMaxValue at index: $maxIndex")
            val categoriesMap = mapOf(
                0 to "World",
                1 to "Entertainment",
                2 to "Science",
                3 to "Health",
                4 to "Business",
                5 to "Sports",
                6 to "Politics",
                7 to "Tech"
            )

            val categoryname=categoriesMap[maxIndex] ?: "Unknown"

            // Return the response
            _category.value= CategoryInfo(categoryname, percentMaxValue!!)
        } catch (e: Exception) {
            Log.d(TAG, "fetchcategory5: " + e.message)
            "Error: ${e.message}"
            _category.value= CategoryInfo("FAILED TO SUMMARIZE", 0.0)
        }

    }}