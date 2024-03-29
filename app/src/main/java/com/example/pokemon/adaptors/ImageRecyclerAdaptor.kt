package com.example.pokemon.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.databinding.ImagelayoutitemBinding
import com.example.pokemon.utils.setImage
import com.example.pokemon.viewholders.ImageViewHolder

class ImageRecyclerAdaptor(val context: Context) : RecyclerView.Adapter<ImageViewHolder>() {
    private var mList: List<String> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ImagelayoutitemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val singleItem = mList[position]
        with(holder.binding) {
            pokiImageView.setImage(singleItem)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun submitList(newList: List<String>) {
        mList = newList
        notifyDataSetChanged()
    }
}