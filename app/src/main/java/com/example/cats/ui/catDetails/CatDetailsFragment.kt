package com.example.cats.ui.catDetails

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.example.cats.R
import com.example.cats.databinding.FragmentCatDetailsBinding
import kotlinx.coroutines.flow.collect
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.*

class CatDetailsFragment : Fragment() {

    private var _binding: FragmentCatDetailsBinding? = null
    private val binding get() = requireNotNull(_binding)


    private val viewModel: CatDetailViewModel by viewModels()

    private var currentId = ""
    private var currentUrl = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        currentId = arguments?.getString(CAT_ID) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCatDetails(currentId)
        setListeners()
    }

    private fun setListeners() {

        lifecycleScope.launch {
            viewModel.result.collect {
                if (it.id != "") {
                    currentUrl = it.url
                    loadImage(currentUrl)
                } else {
                    Toast.makeText(requireActivity(), "Loading error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun loadImage(url: String) {
        binding.catImage.load(url) {
            crossfade(true)
            placeholder(R.mipmap.ic_launcher)
            transformations(CircleCropTransformation())
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

