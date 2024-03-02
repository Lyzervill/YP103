package com.btpit.up103


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken



class PostRepositorySharedPrefsImpl(
    context: Context,
) : PostRepository {
    private val gson = Gson()
    private val prefs = context.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val key = "posts"
    private var nextId = 1
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        prefs.getString(key, null)?.let {
            posts = gson.fromJson(it, type)
            data.value = posts
        }
    }

     override fun getAll(): LiveData<List<Post>> = data
     override fun save(post: Post) {
        if (post.id == 0) {
            posts = listOf(
                post.copy(
                    id = nextId++,
                    autor = "Me",
                    likedByMe = false,
                    published = "now"
                )
            ) + posts
            data.value = posts
            sync()
            return
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
        sync()
    }
    override fun likeById(id: Long) {
        val existingPosts = data.value.orEmpty().toMutableList()
        val index = existingPosts.indexOfFirst { it.id == id.toInt() }
        if (index != -1) {
            val post = existingPosts[index]
            existingPosts[index] = post.copy(
                likedByMe = !post.likedByMe,
                likecount = if (post.likedByMe) post.likecount - 1 else post.likecount + 1
            )
            save(existingPosts[index])
        }
        sync()
    }

    override fun getALL(): LiveData<List<Post>> = data
    override fun LikeById(id: Int) {}

    override fun removeById(id: Int) {}

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id.toInt() }
        data.value = posts
        sync()
    }
    private fun sync() {
        with(prefs.edit()) {
            putString(key, gson.toJson(posts))
            apply()
        }
    }
}