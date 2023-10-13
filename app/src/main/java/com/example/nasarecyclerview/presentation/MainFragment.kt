package com.example.nasarecyclerview.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.nasarecyclerview.databinding.FragmentMainBinding
import com.example.nasarecyclerview.di.DaggerAppComponent
import com.example.nasarecyclerview.entity.MarsRoverPhoto
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import androidx.core.widget.addTextChangedListener

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels {
        DaggerAppComponent.create().mainViewModelFactory()
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val marsPhotoAdapter = MarsPhotoAdapter()
    private val marsPhotosPagedAdapter = MarsPhotosPagedAdapter {marsRoverPhoto -> onItemClick(marsRoverPhoto)}

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        marsPhotosPagedAdapter.retry()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loadBtn.isEnabled = false
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.recyclerView.adapter = marsPhotosPagedAdapter.withLoadStateHeaderAndFooter(PhotosLoadStateAdapter(), PhotosLoadStateAdapter())
        // binding.recyclerView.adapter = marsPhotoAdapter

        binding.inputSolNumber.addTextChangedListener {
            val regex = """[0-9]+""".toRegex()
            val message = Toast.makeText(context, "Введите номер сола", Toast.LENGTH_SHORT)
            if (!regex.matches(it.toString())) {
                message.show()
                binding.loadBtn.isEnabled = false
            } else {
                binding.loadBtn.isEnabled = true
                message.cancel()
            }
        }

        binding.loadBtn.setOnClickListener {
            viewModel.sol = Integer.parseInt(binding.inputSolNumber.text.toString())
            viewModel.loadMarsPhotos(marsPhotosPagedAdapter)
        }

        viewModel.isLoading.onEach {
            binding.swipeRefresh.isRefreshing = it
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.swipeRefresh.setOnRefreshListener {
            marsPhotosPagedAdapter.refresh()
        }

        /*viewLifecycleOwner.lifecycleScope
            .launchWhenStarted {
                viewModel.marsRoverPhotosFlow.collect {
                  // marsPhotoAdapter.setData(it)
                    marsPhotosPagedAdapter.submitList(it.marsPhotosList)
                }
            }*/
    }
    private fun onItemClick(item: MarsRoverPhoto){
        val url = item.imgSrc
        val action = MainFragmentDirections.actionMainFragmentToItemScreenFragment(url)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
