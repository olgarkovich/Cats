package com.example.cats.ui.cats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.cats.CatListener
import com.example.cats.R
import com.example.cats.databinding.ItemCatBinding
import com.example.cats.model.CatImage

class CatsAdapter(private val listener: CatListener) :
    PagingDataAdapter<CatImage, CatsAdapter.CatHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCatBinding.inflate(layoutInflater, parent, false)
        return CatHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: CatHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CatHolder(
        private val binding: ItemCatBinding,
        private val listener: CatListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(catImage: CatImage?) {
            binding.catImage.load(catImage?.url) {
                crossfade(true)
                placeholder(R.mipmap.ic_launcher)
                transformations(CircleCropTransformation())

                initButton(catImage?.id)
            }
        }

        private fun initButton(id: String?) {
            binding.catImage.setOnClickListener {
                if (id != null) {
                    listener.openCat(id)
                }
            }
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<CatImage>() {
        override fun areItemsTheSame(oldItem: CatImage, newItem: CatImage): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CatImage, newItem: CatImage): Boolean {
            return oldItem.id == newItem.id
                && oldItem.url == newItem.url
        }
    }

}
