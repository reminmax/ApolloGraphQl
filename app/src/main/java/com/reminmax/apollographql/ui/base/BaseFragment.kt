package com.reminmax.apollographql.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.reminmax.apollographql.ui.MainActivity
import androidx.navigation.fragment.findNavController

abstract class BaseFragment<T : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : Fragment() {

    private var _binding: T? = null
    protected val binding get() = _binding!!

    protected val navController by lazy {
        (activity as MainActivity).navController
    }

    open fun T.initialize() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DataBindingUtil.inflate<T>(inflater, layoutResId, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.initialize()

        return binding.root
    }

    fun popBackStack() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}