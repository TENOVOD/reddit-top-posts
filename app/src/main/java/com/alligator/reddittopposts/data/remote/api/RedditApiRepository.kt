package com.alligator.reddittopposts.data.remote.api

import com.alligator.reddittopposts.data.remote.model.RedditTopItemsApiResponseDto
import com.alligator.reddittopposts.data.remote.model.RedditUserApiResponseDto

interface RedditApiRepository {

    suspend fun getFirstItems(): RedditTopItemsApiResponseDto

    suspend fun getNextItems(afterIndex:String, count:Int): RedditTopItemsApiResponseDto

    suspend fun getPreviousItems(beforeIndex:String,count: Int): RedditTopItemsApiResponseDto

    suspend fun getAuthorIcon(nickname:String): RedditUserApiResponseDto
}