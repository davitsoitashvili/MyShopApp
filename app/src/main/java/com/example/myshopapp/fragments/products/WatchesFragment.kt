package com.example.myshopapp.fragments.products

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myshopapp.*
import com.example.myshopapp.activities.MyShopDashBoardActivity

import com.example.myshopapp.activities.ProductDetailsActivity
import com.example.myshopapp.adapters.WatchesRecyclerAdapter
import com.example.myshopapp.callbacks.AdapterCallback
import com.example.myshopapp.database.tables.Bags
import com.example.myshopapp.database.tables.Watches
import com.example.myshopapp.fragments.BaseFragment
import com.example.myshopapp.models.BagModel
import com.example.myshopapp.models.ShoesModel
import com.example.myshopapp.models.WatchModel
import com.example.myshopapp.network.ResponseLoader
import kotlinx.android.synthetic.main.fragment_shoes.*
import kotlinx.android.synthetic.main.fragment_shoes.view.*
import kotlinx.android.synthetic.main.fragment_watches.*
import kotlinx.android.synthetic.main.fragment_watches.view.*


class WatchesFragment : BaseFragment(), AdapterCallback {

    private var watchesRecyclerAdapter: WatchesRecyclerAdapter? = null
    private var watchArray: MutableList<WatchModel>? = null

    override fun getFragmentLayout() = R.layout.fragment_watches

    override fun startFragmentConfiguration(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        watchesResponseInformation()
        refreshWatches()
    }

    private fun watchesResponseInformation() {
        ResponseLoader.watchesResponse { r, e ->
            if (r != null) {
                saveWatchesInDatabase(r)
                getWatches()
                updateWatchesRecyclerAdapter()

            } else {
                getWatches()
                updateWatchesRecyclerAdapter()
                Toast.makeText(activity, e, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun manageWatchesRecyclerAdapter() {
        itemView!!.watchesRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        watchesRecyclerAdapter = WatchesRecyclerAdapter(watchArray!!, this)
        itemView!!.watchesRecyclerView.adapter = watchesRecyclerAdapter
        watchesRecyclerAdapter!!.notifyDataSetChanged()


    }

    private fun updateWatchesRecyclerAdapter() {
        Handler().postDelayed(Runnable {
            manageWatchesRecyclerAdapter()
        }, 500)
    }

    private fun saveWatchesInDatabase(watches: MutableList<WatchModel>) {
        AsyncTask.execute {
            MyShopDashBoardActivity.db.getWatches().deleteWatches()
            for (watchModel in watches)
                MyShopDashBoardActivity.db.getWatches().insertAllWatches(
                    Watches(
                        0,
                        watchModel.name,
                        watchModel.imageUrlOne,
                        watchModel.imageUrlTwo,
                        watchModel.imageUrlThree,
                        watchModel.price,
                        watchModel.time
                    )
                )
        }
    }

    private fun getWatches() {
        AsyncTask.execute {
            val watchesList = MyShopDashBoardActivity.db.getWatches().getAllWatches()
            watchArray = mutableListOf()
            for (shoes in watchesList) {
                watchArray!!.add(
                    WatchModel(
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

    private fun refreshWatches() {
        itemView!!.watchesRefresh.setOnRefreshListener {
            Handler().postDelayed(Runnable {
                clearWatches()
                watchesResponseInformation()
                watchesRefresh.isRefreshing = false
            }, 1000)
        }
    }


    private fun clearWatches() {
        watchArray?.clear()
        watchesRecyclerAdapter?.notifyDataSetChanged()
    }

    override fun getProductPosition(position: Int) {
        val intent = Intent(activity, ProductDetailsActivity::class.java)
        intent.putExtra(PRODUCT_MODEL_CODE, watchArray!![position])
        intent.putExtra(MODEL_IDENTIFIER, WATCH_MODEL_IDENTIFIER_CODE)
        startActivity(intent)
    }

}
