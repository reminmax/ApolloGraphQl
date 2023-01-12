package com.reminmax.apollographql

import com.google.common.io.CharStreams
import okhttp3.mockwebserver.MockResponse
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.Charset

object Utils {

    @Throws(IOException::class)
    fun readFileToString(
        contextClass: Class<*>,
        streamIdentifier: String
    ): String = InputStreamReader(
        contextClass.getResourceAsStream(streamIdentifier),
        Charset.defaultCharset()
    ).use {
        CharStreams.toString(it)
    }

    @Throws(IOException::class)
    fun mockResponse(fileName: String) =
        MockResponse().setChunkedBody(
            readFileToString(Utils::class.java, "/$fileName"), 32
        )
}