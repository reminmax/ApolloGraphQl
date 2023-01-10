package com.reminmax.apollographql.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.exception.ApolloException
import com.reminmax.apollographql.CharactersListQuery
import com.reminmax.apollographql.data.repository.CharacterRepository
import com.reminmax.apollographql.ui.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ViewState<ApolloResponse<CharactersListQuery.Data>>>(ViewState.Loading())
    val uiState: StateFlow<ViewState<ApolloResponse<CharactersListQuery.Data>>> = _uiState

    fun queryCharactersList() = viewModelScope.launch {
        _uiState.emit(ViewState.Loading())
        try {
            val response = repository.queryCharactersList()
            _uiState.emit(ViewState.Success(response))
        } catch (e: ApolloException) {
            Log.d("ApolloException", "Failure", e)
            _uiState.emit(ViewState.Error("Error occurred while fetching data"))
        }
    }
}