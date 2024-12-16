package com.alligator.reddittopposts.domain.model

data class Post(
    val authorName:String?,
    val authorIcon:String?,
    val title:String?,
    val created:String?,
    val description:String?,
    val countOfComments:Int?,
    val thumbnail:String?,
    val stockImage:String?
)