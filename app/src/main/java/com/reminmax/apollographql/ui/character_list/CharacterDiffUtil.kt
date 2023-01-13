package com.reminmax.apollographql.ui.character_list

import androidx.recyclerview.widget.DiffUtil
import com.reminmax.apollographql.CharactersListQuery

object CharacterDiffUtil : DiffUtil.ItemCallback<CharactersListQuery.Result>() {

    override fun areItemsTheSame(
        oldItem: CharactersListQuery.Result,
        newItem: CharactersListQuery.Result
    ) = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: CharactersListQuery.Result,
        newItem: CharactersListQuery.Result
    ) = oldItem == newItem
}