package com.app.exoplayerdemo.ui.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.exoplayerdemo.R
import com.app.exoplayerdemo.databinding.FragmentVideosBinding
import com.app.exoplayerdemo.utils.EXTRAS_VIDEOS
import com.app.exoplayerdemo.viewModels.VideosViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideosFragment : Fragment() {

    private val videosViewModel: VideosViewModel by viewModels()
    private val adapter: VideosAdapter by lazy {
        VideosAdapter { _, videoInfo ->
            findNavController().navigate(
                R.id.action_VideoFragment_to_PlayerFragment,
                bundleOf(
                    EXTRAS_VIDEOS to videoInfo
                )
            )
        }
    }
    private var _binding: FragmentVideosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter
        videosViewModel.videos.observe(viewLifecycleOwner) {
            adapter.addAll(it)
        }

        childFragmentManager.beginTransaction()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}