package com.reminmax.apollographql.data.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.reminmax.apollographql.CharactersListQuery
import com.reminmax.apollographql.data.network.RickAndMortyApi
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val webService: RickAndMortyApi
) : ICharacterRepository {

    override suspend fun queryCharactersList(): ApolloResponse<CharactersListQuery.Data> {
        return webService.getApolloClient().query(CharactersListQuery()).execute()
    }

}