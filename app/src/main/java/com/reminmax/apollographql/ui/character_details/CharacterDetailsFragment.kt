package com.reminmax.apollographql.ui.character_details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.reminmax.apollographql.R
import com.reminmax.apollographql.databinding.FragmentCharacterDetailsBinding
import com.reminmax.apollographql.ui.CharacterViewModel
import com.reminmax.apollographql.ui.base.BaseFragment
import com.reminmax.apollographql.ui.state.ViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterDetailsFragment :
    BaseFragment<FragmentCharacterDetailsBinding>(R.layout.fragment_character_details) {

    private val viewModel: CharacterViewModel by activityViewModels()
    private val args: CharacterDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        collectUiState()
    }

    private fun initView() {
        viewModel.loadCharacterState(args.id)
        binding.viewModel = viewModel
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.characterState
                .flowWithLifecycle(
                    lifecycle = viewLifecycleOwner.lifecycle,
                    minActiveState = Lifecycle.State.STARTED
                )
                .collect { viewState ->
                    when (viewState) {
                        is ViewState.Success -> {
                            if (viewState.value?.character == null) {
                                binding.characterDetailsFetchProgress.visibility = View.GONE
                            } else {
                                binding.query = viewState.value
                                binding.characterDetailsFetchProgress.visibility = View.GONE
                            }
                        }
                        is ViewState.Error -> {
                            Toast.makeText(context, viewState.message, Toast.LENGTH_SHORT).show()
                        }
                        is ViewState.Loading -> Unit
                    }
                }
        }
    }
}