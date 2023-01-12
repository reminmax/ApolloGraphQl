package com.reminmax.apollographql.ui.character_list

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.reminmax.apollographql.CharactersListQuery

class CharacterAdapter :
    ListAdapter<CharactersListQuery.Result, CharacterViewHolder>(CharacterDiffUtil) {

    var onItemClicked: ((CharactersListQuery.Result) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder =
        CharacterViewHolder.create(parent)

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
        holder.itemView.setOnClickListener {
            onItemClicked?.invoke(character)
        }
    }
}

