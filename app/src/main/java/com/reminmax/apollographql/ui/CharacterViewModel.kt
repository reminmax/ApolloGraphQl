package com.reminmax.apollographql.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reminmax.apollographql.CharacterQuery
import com.reminmax.apollographql.CharactersListQuery
import com.reminmax.apollographql.Utils.WhileUiSubscribed
import com.reminmax.apollographql.data.repository.CharacterRepository
import com.reminmax.apollographql.di.IoDispatcher
import com.reminmax.apollographql.ui.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository,
    //private val resourceProvider: IResourceProvider,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _characterState =
        MutableStateFlow<ViewState<CharacterQuery.Data>>(ViewState.Loading())
    val characterState = _characterState.asStateFlow()

    val characterListState: StateFlow<ViewState<CharactersListQuery.Data>> = flow {
        val response = repository.queryCharactersList()
        response?.let { data ->
            emit(data)
        }
    }.flowOn(ioDispatcher)
        .stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = ViewState.Loading()
        )

    fun loadCharacterState(id: String) {
        viewModelScope.launch(ioDispatcher) {
            val response = repository.queryCharacter(id)
            response?.let { data ->
                _characterState.emit(data)
            }
        }
    }
}