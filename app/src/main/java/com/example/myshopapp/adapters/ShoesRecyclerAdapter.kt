package com.example.myshopapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myshopapp.R
import com.example.myshopapp.callbacks.AdapterCallback
import com.example.myshopapp.databinding.ShoesRecyclerItemBinding
import com.example.myshopapp.models.ShoesModel
import kotlinx.android.synthetic.main.shoes_recycler_item.view.*

class ShoesRecyclerAdapter(
    private val shoesArray: MutableList<ShoesModel>,
    val adapterCallback: AdapterCallback
) :
    RecyclerView.Adapter<ShoesRecyclerAdapter.ShoesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoesViewHolder {
        val shoesBinding: ShoesRecyclerItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.shoes_recycler_item,
            parent,
            false
        )
        return ShoesViewHolder(shoesBinding)
    }

    override fun getItemCount() = shoesArray.size

    override fun onBindViewHolder(holder: ShoesViewHolder, position: Int) {
        holder.onBindShoesItem()
        holder.getShoesPosition()
    }

    inner class ShoesViewHolder(private val shoesBinding: ShoesRecyclerItemBinding) :
        RecyclerView.ViewHolder(shoesBinding.root) {
        fun onBindShoesItem() {
            val shoesModel = shoesArray[adapterPosition]
            shoesBinding.shoesModel = shoesModel
        }

        fun getShoesPosition() {
            shoesBinding.root.shoesImageView.setOnClickListener {
                adapterCallback.getProductPosition(adapterPosition)
            }
        }
    }
}