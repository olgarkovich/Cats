package com.example.cats.ui.cats

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.cats.R
import com.example.cats.databinding.ItemCatBinding
import com.example.cats.model.Cat

class CatsAdapter : RecyclerView.Adapter<CatsAdapter.CatHolder>() {

    private var cats: List<Cat> = emptyList()
    var listener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCatBinding.inflate(layoutInflater, parent, false)
        return CatHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCatsList(cats: List<Cat>) {
        this.cats = cats
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CatHolder, position: Int) {
        holder.bind(cats[position])
    }

    inner class CatHolder(private val binding: ItemCatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cat: Cat) {
            binding.catImage.load(cat.url) {
                crossfade(true)
                placeholder(R.mipmap.ic_launcher)
                transformations(CircleCropTransformation())
            }
        }

        init {
            binding.catImage.setOnClickListener {
                listener?.invoke(cats[adapterPosition].id)
            }
        }
    }

    override fun getItemCount(): Int = cats.size

}
