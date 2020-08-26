package com.example.myshopapp.fragments.products

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myshopapp.*
import com.example.myshopapp.activities.MyShopDashBoardActivity

import com.example.myshopapp.activities.ProductDetailsActivity
import com.example.myshopapp.adapters.ShoesRecyclerAdapter
import com.example.myshopapp.callbacks.AdapterCallback
import com.example.myshopapp.database.tables.Shoes
import com.example.myshopapp.fragments.BaseFragment
import com.example.myshopapp.models.ShoesModel
import com.example.myshopapp.network.ResponseLoader
import kotlinx.android.synthetic.main.fragment_shoes.*
import kotlinx.android.synthetic.main.fragment_shoes.view.*

class ShoesFragment : BaseFragment(), AdapterCallback {
    private var shoesRecyclerAdapter: ShoesRecyclerAdapter? = null
    private var shoesArray: MutableList<ShoesModel>? = null

    override fun getFragmentLayout() = R.layout.fragment_shoes

    override fun startFragmentConfiguration(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        shoesResponseInformation()
        refreshShoes()
    }

    private fun shoesResponseInformation() {
        ResponseLoader.shoesResponse { r, e ->
        if (r != null) {
            saveShoesInDatabase(r)
            getShoes()
            updateShoesRecyclerAdapter()

        } else {
            getShoes()
            updateShoesRecyclerAdapter()
            Toast.makeText(activity, e, Toast.LENGTH_LONG).show()
        }
    }
}

    private fun updateShoesRecyclerAdapter() {
        Handler().postDelayed(Runnable {
            manageShoesRecyclerAdapter()
        }, 500)
    }

    private fun saveShoesInDatabase(shoes: MutableList<ShoesModel>) {
        AsyncTask.execute {
            MyShopDashBoardActivity.db.getShoes().deleteShoes()
            for (shoesModel in shoes)
                MyShopDashBoardActivity.db.getShoes().insertAllShoes(
                    Shoes(
                        0,
                        shoesModel.name,
                        shoesModel.imageUrlOne,
                        shoesModel.imageUrlTwo,
                        shoesModel.imageUrlThree,
                        shoesModel.price,
                        shoesModel.time
                    )
                )
        }
    }

    private fun getShoes() {
        AsyncTask.execute {
            val shoesList = MyShopDashBoardActivity.db.getShoes().getAllShoes()
            shoesArray = mutableListOf()
            for (shoes in shoesList) {
                shoesArray!!.add(
                    ShoesModel(
                        shoes.name,
                        shoes.imageUrlOne,
                        shoes.imageUrlTwo,
                        shoes.imageUrlThree,
                        shoes.price,
                        shoes.time
                    )
                )
            }
        }
    }

    private fun manageShoesRecyclerAdapter() {
        itemView!!.shoesRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        shoesRecyclerAdapter = ShoesRecyclerAdapter(shoesArray!!, this)
        itemView!!.shoesRecyclerView.adapter = shoesRecyclerAdapter
        shoesRecyclerAdapter?.notifyDataSetChanged()

    }

    private fun refreshShoes() {
        itemView!!.shoesRefresh.setOnRefreshListener {
            Handler().postDelayed(Runnable {
                clearShoes()
                shoesResponseInformation()
                shoesRefresh.isRefreshing = false
            }, 1000)
        }
    }

    private fun clearShoes() {
        shoesArray?.clear()
        shoesRecyclerAdapter?.notifyDataSetChanged()
    }

    override fun getProductPosition(position: Int) {
        val intent = Intent(activity, ProductDetailsActivity::class.java)
        intent.putExtra(PRODUCT_MODEL_CODE, shoesArray!![position])
        intent.putExtra(MODEL_IDENTIFIER, SHOES_MODEL_IDENTIFIER_CODE)
        startActivity(intent)
    }


}
