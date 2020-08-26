package com.example.myshopapp.fragments.products

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myshopapp.BAGS_MODEL_IDENTIFIER_CODE
import com.example.myshopapp.MODEL_IDENTIFIER
import com.example.myshopapp.PRODUCT_MODEL_CODE

import com.example.myshopapp.R
import com.example.myshopapp.activities.MyShopDashBoardActivity
import com.example.myshopapp.activities.ProductDetailsActivity
import com.example.myshopapp.adapters.BagsRecyclerAdapter
import com.example.myshopapp.callbacks.AdapterCallback
import com.example.myshopapp.database.tables.Bags
import com.example.myshopapp.database.tables.Shoes
import com.example.myshopapp.fragments.BaseFragment
import com.example.myshopapp.models.BagModel
import com.example.myshopapp.models.ShoesModel
import com.example.myshopapp.network.ResponseLoader
import kotlinx.android.synthetic.main.fragment_bags.*
import kotlinx.android.synthetic.main.fragment_bags.view.*
import kotlinx.android.synthetic.main.fragment_shoes.*
import kotlinx.android.synthetic.main.fragment_shoes.view.*
import kotlinx.android.synthetic.main.fragment_watches.*


class BagsFragment : BaseFragment(), AdapterCallback {

    private var bagsRecyclerAdapter: BagsRecyclerAdapter? = null
    private var bagsArray: MutableList<BagModel>? = null

    override fun getFragmentLayout() = R.layout.fragment_bags

    override fun startFragmentConfiguration(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        bagsResponseInformation()
        refreshBags()
    }

    private fun bagsResponseInformation() {
        ResponseLoader.bagsResponse { r, e ->
            if (r != null) {
                saveBagsInDatabase(r)
                getBags()
                updateBagsRecyclerAdapter()

            } else {
                getBags()
                updateBagsRecyclerAdapter()
                Toast.makeText(activity, e, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updateBagsRecyclerAdapter() {
        Handler().postDelayed(Runnable {
            manageBagsRecyclerAdapter()
        }, 500)
    }

    private fun saveBagsInDatabase(bags: MutableList<BagModel>) {
        AsyncTask.execute {
            MyShopDashBoardActivity.db.getBags().deleteBags()
            for (bagsModel in bags)
                MyShopDashBoardActivity.db.getBags().insertAllBags(
                    Bags(
                        0,
                        bagsModel.name,
                        bagsModel.imageUrlOne,
                        bagsModel.imageUrlTwo,
                        bagsModel.imageUrlThree,
                        bagsModel.price,
                        bagsModel.time
                    )
                )
        }
    }

    private fun getBags() {
        AsyncTask.execute {
            val bagsList = MyShopDashBoardActivity.db.getBags().getAllBags()
            bagsArray = mutableListOf()
            for (bag in bagsList) {
                bagsArray!!.add(
                    BagModel(
                        bag.name,
                        bag.imageUrlOne,
                        bag.imageUrlTwo,
                        bag.imageUrlThree,
                        bag.price,
                        bag.time
                    )
                )
            }
        }
    }

    private fun manageBagsRecyclerAdapter() {
        itemView!!.bagsRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        bagsRecyclerAdapter = BagsRecyclerAdapter(bagsArray!!, this)
        itemView!!.bagsRecyclerView.adapter = bagsRecyclerAdapter
        bagsRecyclerAdapter!!.notifyDataSetChanged()


    }

    private fun refreshBags() {
        itemView!!.bagsRefresh.setOnRefreshListener {
            Handler().postDelayed(Runnable {
                clearBags()
                bagsResponseInformation()
                bagsRefresh.isRefreshing = false
            }, 1000)
        }
    }

    private fun clearBags() {
        bagsArray?.clear()
        bagsRecyclerAdapter?.notifyDataSetChanged()
    }

    override fun getProductPosition(position: Int) {
        val intent = Intent(activity, ProductDetailsActivity::class.java)
        intent.putExtra(PRODUCT_MODEL_CODE, bagsArray!![position])
        intent.putExtra(MODEL_IDENTIFIER, BAGS_MODEL_IDENTIFIER_CODE)
        startActivity(intent)
    }

}
