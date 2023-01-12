package com.reminmax.apollographql.ui.character_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.reminmax.apollographql.CharactersListQuery
import com.reminmax.apollographql.R
import com.reminmax.apollographql.databinding.CharacterItemBinding

class CharacterAdapter :
    ListAdapter<CharactersListQuery.Result, CharacterViewHolder>(CharacterDiffUtil()) {

    var onItemClicked: ((CharactersListQuery.Result) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding: CharacterItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.character_item,
            parent,
            false
        )
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.binding.character = getItem(position)

        val character = getItem(position)
        holder.binding.root.setOnClickListener {
            onItemClicked?.invoke(character)
        }
    }
}

class CharacterViewHolder(val binding: CharacterItemBinding) : RecyclerView.ViewHolder(binding.root)
