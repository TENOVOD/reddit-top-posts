package com.alligator.reddittopposts.data.mappers

import com.alligator.reddittopposts.data.remote.model.RedditTopItemsApiResponseDto
import com.alligator.reddittopposts.data.remote.model.RedditUserApiResponseDto
import com.alligator.reddittopposts.domain.model.Post

object ItemMapper {

    fun mapToDomain(itemDto:RedditTopItemsApiResponseDto.Item.Child.ChildData, userDto:RedditUserApiResponseDto.UserData):Post{

        return Post(
            authorName = itemDto.subredditNameWithPrefix,
            authorIcon = userDto.iconImage.substringBefore("?"),
            title = itemDto.title,
            created = itemDto.created,
            description = itemDto.description,
            countOfComments = itemDto.numberComments,
            thumbnail = itemDto.thumbnail,
            stockImage = itemDto.url,
            isVideo = itemDto.isVideo,
            videoUrl = itemDto.media?.redditVideo?.fallbackUrl
        )
    }

}