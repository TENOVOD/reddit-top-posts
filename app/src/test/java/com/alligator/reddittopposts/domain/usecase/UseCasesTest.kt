package com.alligator.reddittopposts.domain.usecase

import com.alligator.reddittopposts.data.remote.api.RedditApiService
import com.alligator.reddittopposts.data.repository.impl.DataRepositoryImpl
import com.alligator.reddittopposts.di.module.HttpModule
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class UseCasesTest{

    @Test
    fun shouldReturn10Posts()= runBlocking{
        val client = HttpModule.provideHttpClient()
        val service = RedditApiService(client)
        val repo = DataRepositoryImpl(service)

        val posts = GetNextPosts(repo).invoke()
        println(posts)
        assertEquals(10, posts?.size)
    }
}