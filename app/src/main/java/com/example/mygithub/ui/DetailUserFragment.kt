package com.example.mygithub.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mygithub.R
import com.example.mygithub.databinding.FragmentDetailUserBinding

class DetailUserFragment : Fragment() {

    private var _binding: FragmentDetailUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailUserBinding.inflate(inflater, container, false)
        val view = binding.root

        val username = arguments?.getString("userName")
        binding.tvDetailFollowers.text = username

        // Inflate the layout for this fragment
        return view
    }

}