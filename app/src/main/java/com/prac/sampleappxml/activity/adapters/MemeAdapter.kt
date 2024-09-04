package com.prac.sampleappxml.activity.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.prac.sampleappxml.activity.models.Meme
import com.prac.sampleappxml.databinding.ListitemContentBinding

class MemeAdapter(
    private val context: Context,
    private val list: List<Meme>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MemeViewHolder(
            ListitemContentBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MemeViewHolder).bindView(position = position)
    }

    inner class MemeViewHolder(
        private val binding: ListitemContentBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bindView(position: Int) {
            binding.apply {

                val item = list[position]

                // set title
                titleTv.text = item.title

                // set subtitle
                subtitleTv.text = item.subTitle

                // set image
                Glide.with(context).load(item.image).into(iconIv)

                // handle item click
                root.setOnClickListener {
                    listener.onItemClicked(item)
                }
            }
        }
    }
}

// interface for item click callback
interface OnItemClickListener {
    fun onItemClicked(item: Meme)
}