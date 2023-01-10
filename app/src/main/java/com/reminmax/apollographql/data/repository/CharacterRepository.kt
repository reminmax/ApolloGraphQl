package com.reminmax.apollographql.data.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.reminmax.apollographql.CharactersListQuery

class CharacterRepository : ICharacterRepository {

    override suspend fun queryCharactersList(): ApolloResponse<CharactersListQuery.Data> {
        TODO("Not yet implemented")
    }

}