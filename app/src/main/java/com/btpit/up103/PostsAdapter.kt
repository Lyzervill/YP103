package com.btpit.up103

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.btpit.up103.databinding.PostCardBinding

typealias onInteractionListener = (post:Post)-> Unit

interface OnInteractionListener{
    fun onLike(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onShare(post: Post) {}

}
 class PostViewHolder(
     private val binding: PostCardBinding,
     private val onInteractionListener: OnInteractionListener
 ) : RecyclerView.ViewHolder(binding.root){
     fun  bind(post: Post) {
         binding.apply {
             textViewAutor.text = post.autor
             textViewContent.text = post.content
             imageButtonLike.text = "${post.likecount}"
             imMenu.setOnClickListener {
                 PopupMenu(it.context, it).apply {
                     inflate(R.menu.menu_p)
                     setOnMenuItemClickListener { item ->
                         when (item.itemId){
                             R.id.remove -> {
                                 onInteractionListener.onRemove(post)
                                 true
                             }

                             R.id.edit->{
                                 onInteractionListener.onEdit(post)
                                 true
                             }
                             else -> false
                         }

                     }
                 }.show()
             }
             imageButtonLike.isClickable = !post.likedByMe

            imageButtonLike.setOnClickListener {
                onInteractionListener.onLike(post)
            }
             imageButtonShare.setOnClickListener {
                 onInteractionListener.onShare(post)
             }
         }
     }

 }
class PostsAdapter(
    private val     onInteractionListener: OnInteractionListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding,  onInteractionListener)
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
