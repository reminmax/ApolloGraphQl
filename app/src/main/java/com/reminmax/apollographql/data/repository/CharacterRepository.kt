package com.reminmax.apollographql.data.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.reminmax.apollographql.CharacterQuery
import com.reminmax.apollographql.CharactersListQuery
import com.reminmax.apollographql.ui.state.ViewState
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val apolloClient: ApolloClient
) : ICharacterRepository {

    override suspend fun queryCharactersList(): ViewState<CharactersListQuery.Data>? {
        var result: ViewState<CharactersListQuery.Data>? = null
        try {
            val response = apolloClient.query(CharactersListQuery()).execute()
            response.let {
                it.data?.let { data -> result = ViewState.Success(data) }
            }
        } catch (ex: ApolloException) {
            return ViewState.Error(message = ex.message)
        }
        return result
    }

    override suspend fun queryCharacter(id: String): ViewState<CharacterQuery.Data>? {
        var result: ViewState<CharacterQuery.Data>? = null
        try {
            val response = apolloClient.query(CharacterQuery(id)).execute()
            response.let {
                it.data?.let { data -> result = ViewState.Success(data) }
            }
        } catch (ex: ApolloException) {
            return ViewState.Error(message = ex.message)
        }
        return result
    }
}