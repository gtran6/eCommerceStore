package com.myprojects.ecommercestore.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.myprojects.ecommercestore.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val item = args.data

        Glide.with(requireContext()).load(item.image).into(_binding!!.itemImage)
        _binding!!.apply {
            title.text = item.title
            description.text = item.description
            price.text = "$${item.price}"
            category.text = item.category
            rating.rating = item.rating.rate.toFloat()
            count.text = item.rating.count.toString()
        }
        return root
    }
}