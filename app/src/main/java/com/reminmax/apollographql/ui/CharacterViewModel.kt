package com.reminmax.apollographql.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.exception.ApolloException
import com.reminmax.apollographql.CharactersListQuery
import com.reminmax.apollographql.R
import com.reminmax.apollographql.Utils.WhileUiSubscribed
import com.reminmax.apollographql.data.repository.CharacterRepository
import com.reminmax.apollographql.di.IoDispatcher
import com.reminmax.apollographql.ui.helpers.resource_provider.IResourceProvider
import com.reminmax.apollographql.ui.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository,
    private val resourceProvider : IResourceProvider,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val uiState: StateFlow<ViewState<ApolloResponse<CharactersListQuery.Data>>> = flow {
        try {
            emit(ViewState.Success(repository.queryCharactersList()))
        } catch (ex: ApolloException) {
            Timber.e(ex)
            emit(ViewState.Error(
                message = resourceProvider.getString(R.string.errorOccurredWhileFetchingData))
            )
        }
    }.flowOn(ioDispatcher)
        .stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = ViewState.Loading()
        )


}