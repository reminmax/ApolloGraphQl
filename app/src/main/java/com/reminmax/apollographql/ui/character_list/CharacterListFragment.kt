package com.reminmax.apollographql.ui.character_list

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.reminmax.apollographql.R
import com.reminmax.apollographql.databinding.FragmentCharacterListBinding
import com.reminmax.apollographql.ui.CharacterViewModel
import com.reminmax.apollographql.ui.base.BaseFragment
import com.reminmax.apollographql.ui.helpers.EmptyDataObserver
import com.reminmax.apollographql.ui.state.ViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment :
    BaseFragment<FragmentCharacterListBinding>(R.layout.fragment_character_list) {

    private val viewModel: CharacterViewModel by activityViewModels()
    private lateinit var recyclerViewEmptyDataObserver: EmptyDataObserver
    private var characterAdapter: CharacterAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setupAdapters()
        collectUiState()
    }

    private fun initView() {
        characterAdapter = CharacterAdapter()
        binding.viewModel = viewModel
    }

    private fun setupAdapters() {
        with(binding) {
            rvCharacters.adapter = characterAdapter
            rvCharacters.setHasFixedSize(true)
        }
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState
                .flowWithLifecycle(
                    lifecycle = viewLifecycleOwner.lifecycle,
                    minActiveState = Lifecycle.State.STARTED
                )
                .collectLatest { viewState ->
                    when (viewState) {
                        is ViewState.Success -> {
                            val result = viewState.value?.data?.characters?.results
                            characterAdapter?.submitList(
                                if (result?.size != 0)
                                    result
                                else emptyList()
                            )
                        }
                        is ViewState.Error -> {

                        }
                        is ViewState.Loading -> Unit
                    }
                }
        }
    }

    override fun onResume() {
        super.onResume()

        with(binding) {
            // RecyclerView empty state observer
            recyclerViewEmptyDataObserver = EmptyDataObserver(rvCharacters, emptyDataParent)
            characterAdapter?.registerAdapterDataObserver(recyclerViewEmptyDataObserver)
        }

    }

    override fun onStop() {
        super.onStop()
        characterAdapter?.unregisterAdapterDataObserver(recyclerViewEmptyDataObserver)
    }
}