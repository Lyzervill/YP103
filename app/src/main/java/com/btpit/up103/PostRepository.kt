package com.btpit.up103

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

interface PostRepositoryInterface {
    fun getALL(): LiveData<List<Post>>
    fun LikeById(id: Int)
    fun removeById(id: Int)
    fun save(post: Post)



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
    ).reversed()
    private val data = MutableLiveData(posts)

    override fun getALL(): LiveData<List<Post>> = data
    override fun LikeById(id: Int) {
        posts = posts.map {
            if (it.id != id) it else it.copy(likedByMe = !it.likedByMe)
        }
        data.value = posts
    }
    override fun removeById(id: Int) {
        posts = posts.filter{it.id != id}
        data.value = posts
    }

    private var nextId1 = 0
    override fun save(post: Post) {
        val existingPosts = data.value.orEmpty().toMutableList()
        if(post.id == 0){
            val newPost = post.copy(id = ++nextId1)
            existingPosts.add(0, newPost)
        }
        else{
            val index = existingPosts.indexOfFirst { it.id == post.id }
            if (index != -1) {
                existingPosts[index] = post
            }
        }
       data.value = existingPosts
      }



}
private val empty = Post(
    id = 0,
    autor = "",
    content = "",
    published = "",
    likecount = 0,
    sharecount = 0,
    likedByMe = false
)
class PostViewModel : ViewModel(){
    private val repository: PostRepositoryInterface = PostRepositoryInMemoryImpl2()
    val data = repository.getALL()
    val edited = MutableLiveData(empty)

    fun save(){
        edited.value?.let{
            repository.save(it)
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }
    fun changeContent(content: String) {
            val text = content.trim()
            if (edited.value?.content == text) {
                return
            }
            edited.value = edited.value?.copy(content = text)

    }

    fun likeById(id: Int) = repository.LikeById(id)
    fun removeById(id: Int) = repository.removeById(id)


}




