package com.btpit.up103

 data class Post (
    val id: Int,
    val autor: String,
    var likecount:Int = 0,
    val published:String,
    val content: String,
    var sharecount: Int = 0,
    val likedByMe: Boolean,
 )
