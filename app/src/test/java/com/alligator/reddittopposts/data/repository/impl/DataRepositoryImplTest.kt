package com.alligator.reddittopposts.data.repository.impl

import com.alligator.reddittopposts.data.remote.api.RedditApiService
import com.alligator.reddittopposts.di.module.HttpModule
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class DataRepositoryImplTest{


    @Test
    fun shouldReturnFirst10Posts()= runBlocking{
        val client = HttpModule.provideHttpClient()
        val service = RedditApiService(client)
        val repo = DataRepositoryImpl(service)

        val expectedListSize = 10
        val actualPosts = repo.getFirstItems()

        val actualPostsSize = actualPosts.size
        assertEquals(expectedListSize,actualPostsSize)
    }

    @Test
    fun shouldReturnNext10Posts()= runBlocking {
        val client = HttpModule.provideHttpClient()
        val service = RedditApiService(client)
        val repo = DataRepositoryImpl(service)

        val expectedListSize = 10
        val actuasdsds = repo.getNextItems()
        val actualPosts = repo.getNextItems()
        println(actualPosts?.get(0))
        val actualPostsSize = actualPosts?.size ?: 0

        assertEquals(expectedListSize,actualPostsSize)
    }
}