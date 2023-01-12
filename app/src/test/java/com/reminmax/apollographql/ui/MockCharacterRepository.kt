package com.reminmax.apollographql.ui

import com.reminmax.apollographql.CharacterQuery
import com.reminmax.apollographql.CharactersListQuery
import com.reminmax.apollographql.data.repository.ICharacterRepository
import com.reminmax.apollographql.ui.state.ViewState

class MockCharacterRepository(
    private val characterList: ViewState<CharactersListQuery.Data>? = null,
    private val character: ViewState<CharacterQuery.Data>? = null
) : ICharacterRepository {

    override suspend fun queryCharactersList(): ViewState<CharactersListQuery.Data>? {
        return characterList
    }

    override suspend fun queryCharacter(id: String): ViewState<CharacterQuery.Data>? {
        return character
    }
}