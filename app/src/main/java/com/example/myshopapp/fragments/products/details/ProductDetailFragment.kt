package com.example.myshopapp.fragments.products.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide

import com.example.myshopapp.R
import com.example.myshopapp.activities.ProductDetailsActivity
import com.example.myshopapp.fragments.BaseFragment
import kotlinx.android.synthetic.main.fragment_product_detail.view.*
import kotlinx.android.synthetic.main.fragment_product_detail.view.imageView
import kotlinx.android.synthetic.main.fragment_product_detail.view.pageView



class ProductDetailFragment : BaseFragment() {

    var page: Int = 0
    lateinit var productImage: String
    lateinit var productDetailsActivity: ProductDetailsActivity

    override fun getFragmentLayout() = R.layout.fragment_product_detail

    override fun startFragmentConfiguration(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        setImage(page, productImage)

    }

    @SuppressLint("SetTextI18n")
    private fun setImage(page: Int, productImage: String?) {
        productDetailsActivity = activity as ProductDetailsActivity
        itemView!!.pageView.text = "$page/${productDetailsActivity.productImages.size}"
        Glide.with(this).load(productImage).into(itemView!!.imageView)
    }


}
