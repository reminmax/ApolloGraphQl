package com.reminmax.apollographql.ui.character_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.reminmax.apollographql.CharactersListQuery
import com.reminmax.apollographql.R
import com.reminmax.apollographql.databinding.CharacterItemBinding

class CharacterViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    private val binding = CharacterItemBinding.bind(itemView)

    fun bind(character: CharactersListQuery.Result) {
        binding.character = character
    }

    companion object {
        fun create(parent: ViewGroup): CharacterViewHolder {
            val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false)
            return CharacterViewHolder(itemView)
        }
    }
}