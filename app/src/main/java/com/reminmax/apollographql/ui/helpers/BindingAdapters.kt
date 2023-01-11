package com.reminmax.apollographql.ui.helpers

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.reminmax.apollographql.CharacterQuery
import com.reminmax.apollographql.CharactersListQuery
import com.reminmax.apollographql.ui.state.ViewState

@BindingAdapter("showWhenLoading")
fun bindShowWhenLoading(view: View, uiState: ViewState<CharactersListQuery.Data>) {
    view.visibility = if (uiState is ViewState.Loading)
        View.VISIBLE
    else View.GONE
}

@BindingAdapter("showWhenDetailsLoading")
fun bindShowWhenDetailsLoading(
    view: View,
    uiState: ViewState<CharacterQuery.Data>
) {
    view.visibility = if (uiState is ViewState.Loading)
        View.VISIBLE
    else View.GONE
}

@BindingAdapter("hideIfErrorOccurred")
fun bindHideIfErrorOccurred(
    view: View,
    uiState: ViewState<CharactersListQuery.Data>
) {
    view.visibility = if (uiState is ViewState.Error)
        View.GONE
    else View.VISIBLE
}

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    imageView.load(url) {
        crossfade(true)
    }
}

@BindingAdapter("showWhenError")
fun bindShowWhenError(view: View, uiState: ViewState<CharacterQuery.Data>) {
    view.visibility = if (uiState is ViewState.Error)
        View.VISIBLE
    else View.GONE
}
