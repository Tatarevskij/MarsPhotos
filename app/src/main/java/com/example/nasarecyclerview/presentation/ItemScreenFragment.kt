package com.example.nasarecyclerview.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.nasarecyclerview.databinding.FragmentItemScreenBinding

private const val ARG_URL = "param1"

class ItemScreenFragment : Fragment() {
    private var _binding: FragmentItemScreenBinding? = null
    private val binding get() = _binding!!
    private var url: String? = null
    private val args: ItemScreenFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ARG_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemScreenBinding.inflate(inflater, container, false)
        Glide.with(this)
            .load(args.url)
            .into(binding.imageScreen)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}