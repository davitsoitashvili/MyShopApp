package com.example.myshopapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.myshopapp.*
import com.example.myshopapp.fragments.authentication.SignInFragment
import com.example.myshopapp.fragments.authentication.SignUpFragment
import kotlinx.android.synthetic.main.activity_order_product.*

class OrderProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_product)
        retrieveProductData()
        checkCardField()
    }

    private fun retrieveProductData() {
        val image = intent.getStringExtra(PRODUCT_IMAGE)
        val price = intent.getIntExtra(PRODUCT_PRICE, 0)
        val totalPrice = price + 5
        setOrderProduct(image!!, price, totalPrice)
    }

    @SuppressLint("SetTextI18n")
    private fun setOrderProduct(image: String, price: Int, totalPrice: Int) {
        Glide.with(App.getAppInstance().applicationContext).load(image)
            .into(orderedProductImageView)
        orderedProductPriceView.text = "Price: $price$"
        totalPriceView.text = "Total: $totalPrice$"
    }

    private fun checkCardField() {
        buyProductBtn.setOnClickListener {
            when {
                cardNumberField.text.length < 16 -> {
                    cardNumberField.error = "Type valid card number!"
                }
                cvvInputField.text.length < 3 -> {
                    cvvInputField.error = "Type valid CVV number!"
                }
                validDateField.text.length < 5 -> {
                    validDateField.error = "Type valid date!"
                }
                !validDateField.text.toString().contains('/')
                -> {
                    validDateField.error = "Type valid date!"
                }
                else -> {
                    successMessage()
                }
            }
        }
    }

    private fun successMessage() {
        successMessage.visibility = View.VISIBLE
        Handler().postDelayed(Runnable {
            openMyShopDashBoardActivity()
        }, 5000)
    }

    private fun openMyShopDashBoardActivity() {
        val intent = Intent(this, MyShopDashBoardActivity::class.java)
        intent.putExtra(EMAIL, MyShopDashBoardActivity.emailAddress)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
