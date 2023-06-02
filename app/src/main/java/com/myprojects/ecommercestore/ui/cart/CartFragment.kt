package com.myprojects.ecommercestore.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.myprojects.ecommercestore.R
import com.myprojects.ecommercestore.databinding.FragmentCartBinding
import com.myprojects.ecommercestore.databinding.FragmentDetailBinding
import com.myprojects.ecommercestore.model.ApiResponseItem
import com.myprojects.ecommercestore.ui.detail.DetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root
        cartViewModel.allAddedItems.observe(viewLifecycleOwner, Observer { list ->
            setRecyclerView(list)
        })

        cartViewModel.cartItemCount.observe(viewLifecycleOwner, Observer { itemCount ->

        })
        return root
    }

    private fun setRecyclerView(list: List<ApiResponseItem>) = _binding!!.cartRcv.apply {
        adapter = CartAdapter(list)
        layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}