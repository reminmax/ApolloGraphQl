package com.reminmax.apollographql.data.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.reminmax.apollographql.CharactersListQuery
import kotlinx.coroutines.flow.Flow

interface ICharacterRepository {

    //suspend fun queryCharactersList(): Flow<ApolloResponse<CharactersListQuery.Data>>
    suspend fun queryCharactersList(): ApolloResponse<CharactersListQuery.Data>

}