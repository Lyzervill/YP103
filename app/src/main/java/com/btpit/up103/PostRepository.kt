package com.btpit.up103

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil

interface PostRepositoryInterface {
    fun getALL(): LiveData<List<Post>>
    fun LikeById(id: Int)
}

class PostRepositoryInMemoryImpl2 : PostRepositoryInterface {
    private var posts = listOf(
        Post(
            id = 2,
            autor = "БТПИТ",
            content = "Траляля",
            published = "18 сентября 10:12",
            likecount = 0,
            sharecount = 0,
            likedByMe = false

        ),
        Post(
            id = 1,
            autor = "БТПИТ",
            content = "Привет, я Николя!",
            published = "21 мая в 18:36",
            likecount = 0,
            sharecount = 0,
            likedByMe = false
        ),
    )
    private val data = MutableLiveData(posts)

    override fun getALL(): LiveData<List<Post>> = data
    override fun LikeById(id: Int) {
        posts = posts.map {
            if (it.id != id) it else it.copy(likedByMe = !it.likedByMe)
        }
        data.value = posts

    }



}
class PostViewModel : ViewModel(){
    private val repository: PostRepositoryInterface = PostRepositoryInMemoryImpl2()
    val data = repository.getALL()
    fun likeById(id: Int) = repository.LikeById(id)
}

