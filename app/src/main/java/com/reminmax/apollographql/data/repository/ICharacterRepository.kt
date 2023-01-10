package com.reminmax.apollographql.data.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.reminmax.apollographql.CharactersListQuery

interface ICharacterRepository {

    suspend fun queryCharactersList(): ApolloResponse<CharactersListQuery.Data>

}