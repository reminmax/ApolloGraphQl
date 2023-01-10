package com.reminmax.apollographql.ui.helpers

import android.view.View
import androidx.databinding.BindingAdapter
import com.apollographql.apollo3.api.ApolloResponse
import com.reminmax.apollographql.CharactersListQuery
import com.reminmax.apollographql.ui.state.ViewState

@BindingAdapter("showWhenLoading")
fun bindVisible(view: View, uiState: ViewState<ApolloResponse<CharactersListQuery.Data>>) {
    view.visibility = if (uiState is ViewState.Loading)
        View.VISIBLE
    else View.GONE
}