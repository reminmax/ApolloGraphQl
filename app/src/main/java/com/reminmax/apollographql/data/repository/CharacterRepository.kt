package com.reminmax.apollographql.data.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.reminmax.apollographql.CharactersListQuery
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val apolloClient: ApolloClient
) : ICharacterRepository {

    override suspend fun queryCharactersList(): ApolloResponse<CharactersListQuery.Data> {
        return apolloClient.query(CharactersListQuery()).execute()
    }
}