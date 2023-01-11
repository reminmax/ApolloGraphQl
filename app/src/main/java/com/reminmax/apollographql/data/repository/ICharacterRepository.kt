package com.reminmax.apollographql.data.repository

import com.reminmax.apollographql.CharacterQuery
import com.reminmax.apollographql.CharactersListQuery
import com.reminmax.apollographql.ui.state.ViewState

interface ICharacterRepository {

    suspend fun queryCharactersList(): ViewState<CharactersListQuery.Data>?

    suspend fun queryCharacter(id: String): ViewState<CharacterQuery.Data>?
}