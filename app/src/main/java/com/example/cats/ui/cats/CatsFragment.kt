package com.example.cats.ui.cats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cats.R
import com.example.cats.databinding.FragmentCatsBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CatsFragment : Fragment() {

    private var _binding: FragmentCatsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: CatsViewModel by viewModels()
    private val adapter = CatsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentCatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setListeners()
    }

    private fun initView() {

        viewModel.getCats()

        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.catsRecycler.layoutManager = layoutManager

        binding.catsRecycler.adapter = adapter

        adapter.listener = {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            val bundle = bundleOf(CAT_ID to it)
            findNavController().navigate(R.id.action_catsFragment_to_catDetailFragment, bundle)
        }
    }

    private fun setListeners() {
        lifecycleScope.launch {
            viewModel.result.collect {
                if (it.isNotEmpty()) {
                    adapter.setCatsList(it)
                } else {
                    Toast.makeText(requireActivity(), "Loading error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private companion object {
        const val CAT_ID = "id"
    }

}
