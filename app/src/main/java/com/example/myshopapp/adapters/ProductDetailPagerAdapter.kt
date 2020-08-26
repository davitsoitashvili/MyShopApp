package com.example.myshopapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.myshopapp.fragments.products.details.ProductDetailFragment

class ProductDetailPagerAdapter(fm: FragmentManager, private val productImages : MutableList<String>) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        val fragment = ProductDetailFragment()
        fragment.page = position + 1
        fragment.productImage = productImages[position]
        return fragment
    }

    override fun getCount() = productImages.size
}