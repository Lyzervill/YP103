package com.btpit.up103

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.btpit.up103.databinding.PostCardBinding

typealias OnLikeListener = (post: Post) -> Unit
 class PostViewHolder (
     private val binding: PostCardBinding,
     private val onLikeListener: OnLikeListener
 ) : RecyclerView.ViewHolder(binding.root){
     fun  bind(post: Post) {
         binding.apply {
             textViewAutor.text = post.autor
             textViewContent.text = post.content
             imageButtonLike.setImageResource(
                 if(post.likedByMe) R.drawable._589054 else R.drawable.icons8__24
             )
             imageButtonLike.setOnClickListener{
                 onLikeListener(post)
             }
         }
     }
 }
class PostsAdapter (
    private  val onLikeListener: OnLikeListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position )
        holder.bind(post)
    }

}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}