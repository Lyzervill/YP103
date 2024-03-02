package com.btpit.up103

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PostRepositoryFileImpl(
    private val context: Context,
) : PostRepository {
    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val filename = "posts.json"
    private var nextId = 1L
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        val file = context.filesDir.resolve(filename)
        if (file.exists()) {
            // если файл есть - читаем
            context.openFileInput(filename).bufferedReader().use {
                posts = gson.fromJson(it, type)
                data.value = posts
            }
        } else {

            sync()
        }
    }
 override fun getAll(): LiveData<List<Post>> = data

    override fun save(post: Post) {
        if (post.id == 0) {
            // TODO: remove hardcoded author & published
            posts = listOf(
                post.copy(
                    id = nextId++.toInt(),
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

  override  fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id.toInt()) it else it.copy(
                likedByMe = !it.likedByMe,
                likecount = if (it.likedByMe) it.likecount - 1 else it.likecount + 1
            )
        }
        data.value = posts
        sync()
    }

    override fun getALL(): LiveData<List<Post>> {
        TODO("Not yet implemented")
    }

    override fun LikeById(id: Int) {
        TODO("Not yet implemented")
    }

    override fun removeById(id: Int) {
        TODO("Not yet implemented")
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id.toInt() }
        data.value = posts
        sync()
    }

    private fun sync() {
        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }
}