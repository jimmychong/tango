package com.example.tango.presentation.post_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tango.databinding.ItemPostBinding
import com.example.tango.domain.model.Post
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PostAdapter @Inject constructor(
    val selectedPostListener: SelectedPostListener
) : ListAdapter<Post, PostAdapter.ViewHolder>(PostListDiffCallback()) {
    class ViewHolder constructor(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post, itemSelectedListener: SelectedPostListener) {
            binding.title.text = post.title
            binding.body.text = post.body
            binding.root.setOnClickListener {
                itemSelectedListener.onClick(post)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemPostBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post, selectedPostListener)
    }
}

class PostListDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.postId == newItem.postId
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}

class SelectedPostListener @Inject constructor() {
    var onItemClick: ((Post) -> Unit)? = null

    fun onClick(post: Post) = onItemClick?.invoke(post)
}