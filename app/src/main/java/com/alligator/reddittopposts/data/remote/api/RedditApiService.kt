package com.alligator.reddittopposts.data.remote.api

import com.alligator.reddittopposts.data.remote.model.RedditTopItemsApiResponseDto
import com.alligator.reddittopposts.data.remote.model.RedditUserApiResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject


class RedditApiService @Inject constructor(
    private val client: HttpClient,
):RedditApiRepository{
    private val TOP_URL = "http://www.reddit.com/top.json"
    private val AUTHOR_URL = "https://www.reddit.com/user/"
    private val ITEMS_LIMIT = 10


    override suspend fun getFirstItems(): RedditTopItemsApiResponseDto {

        return client.get(TOP_URL){
          parameter("limit",ITEMS_LIMIT)
          parameter("count",0)
        }.body()
    }

    override suspend fun getNextItems(
        afterIndex: String,
        count: Int
    ): RedditTopItemsApiResponseDto {
       return client.get(TOP_URL){
           parameter("after",afterIndex)
           parameter("limit",ITEMS_LIMIT)
           parameter("count",count)
       }.body()
    }

    override suspend fun getPreviousItems(
        beforeIndex: String,
        count: Int
    ): RedditTopItemsApiResponseDto {
        return client.get(TOP_URL){
            parameter("before",beforeIndex)
            parameter("limit",ITEMS_LIMIT)
            parameter("count",count)
        }.body()
    }

    override suspend fun getAuthorIcon(nickname: String): RedditUserApiResponseDto {
        val url = "$AUTHOR_URL/$nickname/about.json"
        return client.get(url).body()
    }


}