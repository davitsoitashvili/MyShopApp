package com.example.myshopapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myshopapp.R
import com.example.myshopapp.callbacks.AdapterCallback
import com.example.myshopapp.databinding.BagsRecyclerItemBinding
import com.example.myshopapp.models.BagModel
import com.example.myshopapp.models.ShoesModel
import kotlinx.android.synthetic.main.bags_recycler_item.view.*

class BagsRecyclerAdapter(private val bagsArray: MutableList<BagModel>,val adapterCallback: AdapterCallback) :
    RecyclerView.Adapter<BagsRecyclerAdapter.BagsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BagsViewHolder {
        val bagsBinding: BagsRecyclerItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.bags_recycler_item,
            parent,
            false
        )
        return BagsViewHolder(bagsBinding)
    }

    override fun getItemCount() = bagsArray.size

    override fun onBindViewHolder(holder: BagsViewHolder, position: Int) {
        holder.onBindBagsItem()
        holder.getBagPosition()
    }

    inner class BagsViewHolder(private val bagBinding: BagsRecyclerItemBinding) :
        RecyclerView.ViewHolder(bagBinding.root) {
        fun onBindBagsItem() {
            val bagsModel = bagsArray[adapterPosition]
            bagBinding.bagsModel = bagsModel
        }

        fun getBagPosition() {
            bagBinding.root.bagsImageView.setOnClickListener {
                adapterCallback.getProductPosition(adapterPosition)
            }
        }
    }
}