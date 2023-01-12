package com.reminmax.apollographql.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.reminmax.apollographql.CharacterQuery
import com.reminmax.apollographql.CharactersListQuery
import com.reminmax.apollographql.MainCoroutineRule
import com.reminmax.apollographql.ui.state.ViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class CharacterViewModelTests {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = MainCoroutineRule()
    private val ioDispatcher = testCoroutineRule.testDispatcher

    @Test
    fun `characterState returns correct value after calling loadCharacterState`() = runTest {

        val character = CharacterQuery.Character(
            id = "14",
            name = "Alien Morty",
            status = "unknown",
            species = "Alien",
            type = "",
            gender = "Male",
            image = "https://rickandmortyapi.com/api/character/avatar/14.jpeg"
        )

        val result = ViewState.Success(CharacterQuery.Data(character = character))

        val mockRepository = MockCharacterRepository(
            characterList = null,
            character = result
        )

        val viewModel = CharacterViewModel(mockRepository, ioDispatcher)

        viewModel.loadCharacterState("14")

        assertThat(viewModel.characterState.value).isEqualTo(result)
    }

    @Test
    fun `characterListState returns correct value`() = runTest {

        val characters = listOf(
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

        val result = ViewState.Success(
            CharactersListQuery.Data(characters = CharactersListQuery.Characters(characters))
        )

        val mockRepository = MockCharacterRepository(
            characterList = result,
            character = null
        )

        val viewModel = CharacterViewModel(mockRepository, ioDispatcher)

        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.characterListState.collect(FlowCollector { })
        }

        assertThat(viewModel.characterListState.value).isEqualTo(result)

        collectJob.cancel()
    }
}