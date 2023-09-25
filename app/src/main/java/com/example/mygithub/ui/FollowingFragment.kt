package com.example.mygithub.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mygithub.R
import com.example.mygithub.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        val view = binding.root
        // Inflate the layout for this fragment
        val position = arguments?.getInt(ARG_POSITION)
        val username = arguments?.getString(ARG_USERNAME)



        if (position == 1) {
            // Tampilkan konten Follower sesuai dengan username
            binding.tvFollowing.text = "Followers of $username"
        } else {
            // Tampilkan konten Following sesuai dengan username
            binding.tvFollowing.text = "Following by $username"
        }

        return view
    }
}