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
                @SerialName("subreddit_name_prefixed")
                val subredditNameWithPrefix:String?,
                val author:String?,
                val title:String?,
                val created:String?,
                @SerialName("url_overridden_by_dest")
                val description:String?,
                @SerialName("num_comments")
                val numberComments:Int?,
                val thumbnail:String?,
                val url: String?, //high-res image
                val media:Media?,
                @SerialName("is_video")
                val isVideo:Boolean?
            ){
                @Serializable
                data class Media(
                    @SerialName("reddit_video")
                    val redditVideo:RedditVideo?
                ){
                    @Serializable
                    data class RedditVideo(
                        @SerialName("fallback_url")
                        val fallbackUrl:String?
                    )
                }
            }
        }
    }
}
