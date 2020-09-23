package com.aptopayments.mobile.utils

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class FileReader {

    @Throws(IOException::class)
    fun readFile(jsonFileName: String, directory: String): String {
        val inputStream = this::class.java.getResourceAsStream("/$directory/$jsonFileName")
            ?: throw NullPointerException("Have you added the local resource correctly?, Hint: name it as: $jsonFileName")
        val stringBuilder = StringBuilder()
        var inputStreamReader: InputStreamReader? = null
        try {
            inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var character: Int = bufferedReader.read()
            while (character != -1) {
                stringBuilder.append(character.toChar())
                character = bufferedReader.read()
            }
        } catch (exception: IOException) {
            exception.printStackTrace()
        } finally {
            inputStream.close()
            inputStreamReader?.close()
        }
        return stringBuilder.toString()
    }
}
