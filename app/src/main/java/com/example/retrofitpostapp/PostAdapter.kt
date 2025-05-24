package com.example.retrofitpostapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitpostapp.databinding.ItemPostBinding

class PostAdapter(
    private val list: List<Post>,
    private val onClick: (Post) -> Unit
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = list[position]
        holder.binding.tvTitle.text = post.title
        holder.binding.tvBody.text = post.body
        holder.itemView.setOnClickListener { onClick(post) }
    }

    override fun getItemCount(): Int = list.size
}
