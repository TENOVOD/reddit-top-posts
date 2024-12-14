package com.alligator.reddittopposts.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RedditTopItemsApiResponseDto(
    val kind:String?,
    val data:Item?
){
    @Serializable
    data class Item(
        val after:String?,
        val dist:Int?,
        val children: List<Child>?,
        val before:String?
    ){
        @Serializable
        data class Child(
            val data: ChildData?
        ){
            @Serializable
            data class ChildData(
                val subreddit:String?,
                val author:String?,
                val title:String?,
                val created:String?,
                @SerialName("num_comments")
                val numberComments:Int?,
                val thumbnail:String?,
                val url: String?, //high-res image
            )
        }
    }
}
