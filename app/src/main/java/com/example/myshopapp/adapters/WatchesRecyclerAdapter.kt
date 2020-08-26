package com.example.myshopapp.adapters

import com.example.myshopapp.databinding.WatchRecyclerItemBinding
import com.example.myshopapp.models.WatchModel
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myshopapp.R
import com.example.myshopapp.callbacks.AdapterCallback
import kotlinx.android.synthetic.main.watch_recycler_item.view.*


class WatchesRecyclerAdapter(
    private val watchesArray: MutableList<WatchModel>,
    val adapterCallback: AdapterCallback
) :
    RecyclerView.Adapter<WatchesRecyclerAdapter.WatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchViewHolder {
        val watchBinding: WatchRecyclerItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.watch_recycler_item,
            parent,
            false
        )
        return WatchViewHolder(watchBinding)
    }

    override fun getItemCount() = watchesArray.size

    override fun onBindViewHolder(holder: WatchViewHolder, position: Int) {
        holder.onBindWatchItem()
        holder.getWatchPosition()
    }

    inner class WatchViewHolder(private val watchBinding: WatchRecyclerItemBinding) :
        RecyclerView.ViewHolder(watchBinding.root) {
        fun onBindWatchItem() {
            val watchModel = watchesArray[adapterPosition]
            watchBinding.watchModel = watchModel
        }

        fun getWatchPosition() {
            watchBinding.root.watchImageView.setOnClickListener {
                adapterCallback.getProductPosition(adapterPosition)

            }
        }
    }
}