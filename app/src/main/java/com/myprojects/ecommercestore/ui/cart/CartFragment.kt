package com.myprojects.ecommercestore.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.myprojects.ecommercestore.R
import com.myprojects.ecommercestore.databinding.FragmentCartBinding
import com.myprojects.ecommercestore.databinding.FragmentDetailBinding
import com.myprojects.ecommercestore.model.ApiResponseItem
import com.myprojects.ecommercestore.ui.detail.DetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(), CartAdapter.OnCancelClickListener {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter
    private val args: CartFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root
        cartViewModel.allAddedItems.observe(viewLifecycleOwner, Observer { list ->
            setRecyclerView(list)
        })

        _binding!!.checkout.setOnClickListener {
            val item = args.data
            val data = CartFragmentDirections.actionCartFragmentToCheckoutFragment(item)
            findNavController().navigate(data)
        }

        return root
    }

    private fun setRecyclerView(list: List<ApiResponseItem>) = _binding!!.cartRcv.apply {
        adapter = CartAdapter(list, this@CartFragment)
        layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun OnCancelClick(item: ApiResponseItem) {
        cartViewModel.deleteItem(item)
    }
}