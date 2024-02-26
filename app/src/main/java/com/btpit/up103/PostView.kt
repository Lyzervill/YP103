package com.btpit.up103

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

interface PostRepository{
    fun get(): LiveData<Post>
    fun Like()
}

class PostRepositoryInMemoryImpl : PostRepository {
    var post = Post(
        id = 1,
        autor = "sdlkdklskmd",
        content = "sdwsd",
        published = "ss",
        likecount = 999,
        sharecount = 999,
        likedByMe = true

    )
    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data
    override fun Like() {
        post = post.copy(likedByMe = !post.likedByMe)
        data.value = post
    }
}

class PostViewModel : ViewModel(){
    private val repository:PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.get()
    fun like() = repository.Like()
}