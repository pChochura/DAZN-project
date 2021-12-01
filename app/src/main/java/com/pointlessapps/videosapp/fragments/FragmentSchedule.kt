package com.pointlessapps.videosapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pointlessapps.videosapp.adapters.AdapterVideos
import com.pointlessapps.videosapp.databinding.FragmentScheduleBinding
import com.pointlessapps.videosapp.viewModels.ViewModelVideos

class FragmentSchedule : Fragment() {

    private var _binding: FragmentScheduleBinding? = null
    private val binding by lazy { _binding!! }

    private val viewModelVideos by viewModels<ViewModelVideos>()
    private val adapterVideos = AdapterVideos()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null) {
            _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModelVideos.refreshSchedule()

        binding.listVideos.apply {
            adapter = adapterVideos
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModelVideos.schedule.observe(viewLifecycleOwner) {
            adapterVideos.update(it)
        }
        viewModelVideos.state.observe(viewLifecycleOwner) {
            binding.progress.isVisible = it == ViewModelVideos.State.Loading
        }
    }
}