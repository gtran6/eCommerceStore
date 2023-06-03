package com.myprojects.ecommercestore.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.myprojects.ecommercestore.model.ApiResponseItem
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.myprojects.ecommercestore.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment : Fragment(), HomeAdapter.OnFavoriteClickListener{

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var search = ""
    private val homeViewModel: HomeViewModel by viewModels()
    private val args: HomeFragmentArgs by navArgs()

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

        _binding!!.fab.setOnClickListener {
            val item = args.data
            val data = HomeFragmentDirections.actionNavHomeToCartFragment(item)
            findNavController().navigate(data)
        }

        homeViewModel.getSingleProductData()
        homeViewModel.singleProductData.observe(viewLifecycleOwner, Observer { product ->
            Glide.with(requireContext()).load(product.image).into(_binding!!.itemImage)
            _binding!!.itemTitle.text = product.title
        })

        homeViewModel.getAllProductData("desc")
        homeViewModel.allProductData.observe(viewLifecycleOwner, Observer { list ->
            setRecyclerView(list)
        })

        _binding!!.jeweleryButton.setOnClickListener {
            homeViewModel.getAllJeweleryData()
            homeViewModel.allJeweleryData.observe(viewLifecycleOwner, Observer { list ->
                setRecyclerView(list)
            })
            Toast.makeText(requireContext(), "Jewelery", Toast.LENGTH_SHORT).show()
        }

        _binding!!.electronicButton.setOnClickListener {
            homeViewModel.getAllElectronicData()
            homeViewModel.allElectronicData.observe(viewLifecycleOwner, Observer { list ->
                setRecyclerView(list)
            })
            Toast.makeText(requireContext(), "Electronics", Toast.LENGTH_SHORT).show()
        }

        _binding!!.womenButton.setOnClickListener {
            homeViewModel.getWomenClothingData()
            homeViewModel.womenClothingData.observe(viewLifecycleOwner, Observer { list ->
                setRecyclerView(list)
            })
            Toast.makeText(requireContext(), "Women's Clothing", Toast.LENGTH_SHORT).show()
        }

        _binding!!.menButton.setOnClickListener {
            homeViewModel.getMenClothingData()
            homeViewModel.menClothingData.observe(viewLifecycleOwner, Observer { list ->
                setRecyclerView(list)
            })
            Toast.makeText(requireContext(), "Men's Clothing", Toast.LENGTH_SHORT).show()
        }

        return root
    }

    private fun setRecyclerView(list: List<ApiResponseItem>) = _binding!!.mainRcv.apply {
        adapter = HomeAdapter(list, this@HomeFragment)
        layoutManager = GridLayoutManager(context, 2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onFavoriteClick(item: ApiResponseItem) {
        homeViewModel.addItemToFavorites(item)
    }
}