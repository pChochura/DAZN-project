package com.pointlessapps.videosapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pointlessapps.videosapp.ActivityVideoDetails
import com.pointlessapps.videosapp.adapters.AdapterVideos
import com.pointlessapps.videosapp.databinding.FragmentEventsBinding
import com.pointlessapps.videosapp.models.Video
import com.pointlessapps.videosapp.viewModels.ViewModelVideos

class FragmentEvents : Fragment() {

    private var _binding: FragmentEventsBinding? = null
    private val binding by lazy { _binding!! }

    private val viewModelVideos by viewModels<ViewModelVideos>()
    private val adapterVideos = AdapterVideos().apply {
        setOnItemClickListener(::navigateToVideoDetails)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null) {
            _binding = FragmentEventsBinding.inflate(inflater, container, false)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModelVideos.refreshEvents()

        binding.listVideos.apply {
            adapter = adapterVideos
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModelVideos.events.observe(viewLifecycleOwner) {
            adapterVideos.update(it)
        }
        viewModelVideos.state.observe(viewLifecycleOwner) {
            binding.refreshLayout.isRefreshing = it == ViewModelVideos.State.Loading
        }

        binding.refreshLayout.setOnRefreshListener {
            viewModelVideos.refreshEvents()
        }
    }

    private fun navigateToVideoDetails(video: Video) {
        startActivity(
            Intent(requireActivity(), ActivityVideoDetails::class.java)
                .putExtra(ActivityVideoDetails.KEY, video)
        )
    }
}