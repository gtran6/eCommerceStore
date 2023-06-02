package com.myprojects.ecommercestore.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.myprojects.ecommercestore.databinding.FragmentDetailBinding
import com.myprojects.ecommercestore.ui.cart.CartViewModel
import com.myprojects.ecommercestore.ui.favorite.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
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

            heartIcon.setOnClickListener {

                val scaleAnimation = ScaleAnimation(
                    1.0f, 0.8f,
                    1.0f, 0.8f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
                ).apply {
                    duration = 200
                    repeatMode = Animation.REVERSE
                    repeatCount = 1
                }
                scaleAnimation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {}

                    override fun onAnimationEnd(animation: Animation) {
                        favoriteViewModel.addItem(item)
                    }
                    override fun onAnimationRepeat(animation: Animation) {}
                })
                heartIcon.startAnimation(scaleAnimation)
            }

            addToCart.setOnClickListener {

                val scaleAnimation = ScaleAnimation(
                    1.0f, 0.8f,
                    1.0f, 0.8f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
                ).apply {
                    duration = 200
                    repeatMode = Animation.REVERSE
                    repeatCount = 1
                }
                scaleAnimation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {}

                    override fun onAnimationEnd(animation: Animation) {
                        cartViewModel.addItem(item)
                        Toast.makeText(requireContext(), "Item Added", Toast.LENGTH_SHORT).show()
                        cartViewModel.addItemToCart(item)
                    }

                    override fun onAnimationRepeat(animation: Animation) {}
                })
                addToCart.startAnimation(scaleAnimation)
            }

            buyNow.setOnClickListener {
                val item = args.data
                val data = DetailFragmentDirections.actionDetailFragmentToCartFragment(item)
                findNavController().navigate(data)
            }
        }
        return root
    }
}