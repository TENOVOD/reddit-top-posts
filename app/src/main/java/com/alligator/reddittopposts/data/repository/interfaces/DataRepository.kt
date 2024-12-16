package com.alligator.reddittopposts.data.repository.interfaces

import com.alligator.reddittopposts.domain.model.Post

interface DataRepository {

    suspend fun getFirstItems(): List<Post>

    suspend fun getNextItems(): List<Post>?

    suspend fun getPreviousItems(): List<Post>?

}