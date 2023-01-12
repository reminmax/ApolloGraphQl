package com.reminmax.apollographql.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.google.common.truth.Truth.assertThat
import com.reminmax.apollographql.CharacterQuery
import com.reminmax.apollographql.CharactersListQuery
import com.reminmax.apollographql.MainCoroutineRule
import com.reminmax.apollographql.Utils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

private const val TEST_URL = "/"

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class CharacterRepositoryTest {

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private val ioDispatcher = coroutineRule.testDispatcher

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val mockWebServer = MockWebServer()
    private val okHttpClient = OkHttpClient.Builder()
        .dispatcher(Dispatcher())
        .build()
    private lateinit var apolloClient: ApolloClient
    private lateinit var objectUnderTest: CharacterRepository

    @Before
    fun setUp() {
        mockWebServer.start(8080)

        apolloClient = ApolloClient.Builder().serverUrl(TEST_URL).build()

        apolloClient = ApolloClient.Builder()
            .serverUrl(mockWebServer.url(TEST_URL).toString())
            .dispatcher(ioDispatcher)
            .okHttpClient(okHttpClient)
            .build()

        objectUnderTest = CharacterRepository(apolloClient)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `queryCharactersList gets valid result`() = runTest {
        mockWebServer.enqueue(Utils.mockResponse("characters_list_response.json"))

        val expectedResult = CharactersListQuery.Characters(
            results = listOf(
                CharactersListQuery.Result(
                    id = "1",
                    name = "Rick Sanchez",
                    species = "Human"
                ),
                CharactersListQuery.Result(
                    id = "2",
                    name = "Morty Smith",
                    species = "Human"
                ),
            )
        )

        val actualResult = objectUnderTest.queryCharactersList()

        assertThat(actualResult).isNotNull()
        actualResult?.let {
            assertThat(actualResult.value?.characters).isEqualTo(expectedResult)
        }
    }

    @Test
    fun `queryCharacter gets valid result`() = runTest {
        mockWebServer.enqueue(Utils.mockResponse("specific_character_response.json"))

        val expectedResult = CharacterQuery.Character(
            id = "14",
            name = "Alien Morty",
            status = "unknown",
            species = "Alien",
            type = "",
            gender = "Male",
            image = "https://rickandmortyapi.com/api/character/avatar/14.jpeg"
        )

        val actualResult = objectUnderTest.queryCharacter("14")

        assertThat(actualResult).isNotNull()
        actualResult?.let {
            assertThat(actualResult.value?.character).isEqualTo(expectedResult)
        }
    }

}