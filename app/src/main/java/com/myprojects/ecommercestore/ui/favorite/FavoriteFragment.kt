package com.myprojects.ecommercestore.ui.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.myprojects.ecommercestore.databinding.FragmentFavBinding
import com.myprojects.ecommercestore.model.ApiResponseItem
import com.myprojects.ecommercestore.ui.home.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(), HomeAdapter.OnFavoriteClickListener, FavoriteAdapter.OnDeleteClickListener {

    private var _binding: FragmentFavBinding? = null
    private val binding get() = _binding!!
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavBinding.inflate(inflater, container, false)
        val root: View = binding.root
        favoriteViewModel.allFavoriteItems.observe(viewLifecycleOwner, Observer { list ->
            setRecyclerView(list)
        })
        return root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setRecyclerView(list: List<ApiResponseItem>) {
        val favoriteAdapter = FavoriteAdapter(list, this@FavoriteFragment)
        _binding!!.favRcv.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(requireContext())
            favoriteAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onFavoriteClick(item: ApiResponseItem) {
        favoriteViewModel.addItem(item)
    }

    override fun OnDeleteClick(item: ApiResponseItem) {
        favoriteViewModel.deleteItem(item)
    }
}