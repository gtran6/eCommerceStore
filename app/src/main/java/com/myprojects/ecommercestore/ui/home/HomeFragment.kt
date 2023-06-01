package com.myprojects.ecommercestore.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.myprojects.ecommercestore.databinding.FragmentHomeBinding
import com.myprojects.ecommercestore.model.ApiResponseItem
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.widget.SearchView

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var search = ""
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        _binding!!.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    search = it
                    homeViewModel.getAllProductData(search)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        homeViewModel.getSingleProductData()
        homeViewModel.singleProductData.observe(viewLifecycleOwner, Observer { product ->
            Glide.with(requireContext()).load(product.image).into(_binding!!.itemImage)
            _binding!!.itemTitle.text = product.title
        })

        homeViewModel.getAllProductData("desc")
        homeViewModel.allProductData.observe(viewLifecycleOwner, Observer { list ->
            setRecyclerView(list)
        })

        return root
    }

    private fun setRecyclerView(list: List<ApiResponseItem>) = _binding!!.mainRcv.apply {
        adapter = HomeAdapter(list)
        layoutManager = GridLayoutManager(context, 2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}