package com.example.myshopapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myshopapp.*
import com.example.myshopapp.adapters.ProductDetailPagerAdapter
import com.example.myshopapp.models.BagModel
import com.example.myshopapp.models.ShoesModel
import com.example.myshopapp.models.WatchModel
import kotlinx.android.synthetic.main.activity_product_details.*

class ProductDetailsActivity : AppCompatActivity() {

    lateinit var productImages: MutableList<String>
    lateinit var productName : String
    var price : Int = 0
    private lateinit var productDetailPagerAdapter: ProductDetailPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        init()
    }

    private fun init() {
        getProductDetails()
        manageViewPager()
        setProductName()
        setPrice()
        openOrderProductActivity()

    }

    private fun getProductDetails() {
        productImages = mutableListOf()
        when (intent.getIntExtra(MODEL_IDENTIFIER, -1)) {
            SHOES_MODEL_IDENTIFIER_CODE -> {
                val shoesModel = intent.getParcelableExtra<ShoesModel>(PRODUCT_MODEL_CODE)
                productImages.add(shoesModel!!.imageUrlOne!!)
                productImages.add(shoesModel.imageUrlTwo!!)
                productImages.add(shoesModel.imageUrlThree!!)
                productName = shoesModel.name!!
                price = shoesModel.price!!.toInt()
            }
            BAGS_MODEL_IDENTIFIER_CODE -> {
                val bagsModel = intent.getParcelableExtra<BagModel>(PRODUCT_MODEL_CODE)
                productImages.add(bagsModel!!.imageUrlOne!!)
                productImages.add(bagsModel.imageUrlTwo!!)
                productImages.add(bagsModel.imageUrlThree!!)
                productName = bagsModel.name!!
                price = bagsModel.price!!.toInt()
            }
            WATCH_MODEL_IDENTIFIER_CODE -> {
                val watchModel = intent.getParcelableExtra<WatchModel>(PRODUCT_MODEL_CODE)
                productImages.add(watchModel!!.imageUrlOne!!)
                productImages.add(watchModel.imageUrlTwo!!)
                productImages.add(watchModel.imageUrlThree!!)
                productName = watchModel.name!!
                price = watchModel.price!!.toInt()
            }
        }

    }

    private fun manageViewPager() {
        productDetailPagerAdapter = ProductDetailPagerAdapter(supportFragmentManager, productImages)
        productDetailViewPager.adapter = productDetailPagerAdapter
        productDetailPagerAdapter.notifyDataSetChanged()

    }

    private fun setProductName() {
        productNameView.text = productName
    }

    @SuppressLint("SetTextI18n")
    private fun setPrice() {
        productPriceView.text = "Price: ${price}$"
    }

    private fun openOrderProductActivity() {
        orderProductBtn.setOnClickListener {
            val intent = Intent(this, OrderProductActivity::class.java)
            intent.putExtra(PRODUCT_IMAGE, productImages[0])
            intent.putExtra(PRODUCT_PRICE, price)
            startActivity(intent)
        }
    }

}

