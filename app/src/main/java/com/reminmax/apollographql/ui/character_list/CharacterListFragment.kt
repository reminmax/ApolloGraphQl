package com.reminmax.apollographql.ui.character_list

import android.os.Bundle
import android.view.View
import android.widget.Toast
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
        handleClicks()
    }

    private fun handleClicks() {
        characterAdapter?.onItemClicked = { character ->
            character.let {
                if (!character.id.isNullOrBlank()) {
                    navController.navigate(
                        CharacterListFragmentDirections.navigateToCharacterDetailsFragment(
                            id = character.id
                        )
                    )
                }
            }
        }
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
            viewModel.characterListState
                .flowWithLifecycle(
                    lifecycle = viewLifecycleOwner.lifecycle,
                    minActiveState = Lifecycle.State.STARTED
                )
                .collectLatest { viewState ->
                    when (viewState) {
                        is ViewState.Success -> {
                            val result = viewState.value?.characters?.results
                            characterAdapter?.submitList(
                                if (result?.size != 0)
                                    result
                                else emptyList()
                            )
                        }
                        is ViewState.Error -> {
                            Toast.makeText(context, viewState.message, Toast.LENGTH_SHORT).show()
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