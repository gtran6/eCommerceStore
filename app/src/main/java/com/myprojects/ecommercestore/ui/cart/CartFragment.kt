package com.myprojects.ecommercestore.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myprojects.ecommercestore.R
import com.myprojects.ecommercestore.databinding.FragmentCartBinding
import com.myprojects.ecommercestore.databinding.FragmentDetailBinding
import com.myprojects.ecommercestore.ui.detail.DetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }
}