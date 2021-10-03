package com.example.cats.ui.cats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cats.R
import com.example.cats.databinding.FragmentCatsBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest

class CatsFragment : Fragment() {

    private var _binding: FragmentCatsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: CatsViewModel by viewModels()
    private val adapter = CatsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

        val viewModel = ViewModelProvider(this).get(CatsViewModel::class.java)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.catsRecycler.layoutManager = layoutManager

        binding.catsRecycler.adapter =
            adapter.withLoadStateHeaderAndFooter(LoaderStateAdapter(), LoaderStateAdapter())

        lifecycleScope.launchWhenCreated {
            viewModel.getCats().collectLatest {
                adapter.submitData(it)
            }
        }

        adapter.listener = {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            val bundle = bundleOf(CAT_ID to it)
            findNavController().navigate(R.id.action_catsFragment_to_catDetailFragment, bundle)
        }

        adapter.addLoadStateListener { state ->
            binding.catsRecycler.isVisible = state.refresh != LoadState.Loading
            binding.progressBar.isVisible = state.refresh == LoadState.Loading

            if (state.refresh is LoadState.Error) {
                Snackbar.make(
                    binding.root,
                    (state.refresh as LoadState.Error).error.localizedMessage ?: "error",
                    Snackbar.LENGTH_LONG
                ).show()
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
