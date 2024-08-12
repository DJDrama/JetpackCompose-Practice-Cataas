package com.dj.android.catassjetpackcompose

import com.dj.android.catassjetpackcompose.data.network.CatsAPI
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import mockwebserver3.MockWebServer
import okhttp3.MediaType.Companion.toMediaType
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import java.io.IOException

class CatsAPITest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var catsAPI: CatsAPI

    @Before
    fun setup(){
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = MockRequestDispatcher()
        mockWebServer.start()

        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(
                json.asConverterFactory(
                    contentType = "application/json".toMediaType()
                )
            )
            .build()
        catsAPI = retrofit.create(CatsAPI::class.java)
    }

    @Test
    fun `fetchCats() returns a list of cats`() = runTest {
        val response = catsAPI.fetchCats("cute")
        assert(response.isSuccessful)
    }

    @After
    @Throws(IOException::class)
    fun tearDown(){
        mockWebServer.shutdown()
    }
}