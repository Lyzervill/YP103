package com.btpit.up103

 data class Post (
    val id: Int,
    val autor: String,
    var likecount:Int,
    val published:String,
    val content: String,
    var sharecount: Int,
    var likedByMe: Boolean = false
 )
