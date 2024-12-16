package com.alligator.reddittopposts.domain.usecase

import com.alligator.reddittopposts.data.repository.interfaces.DataRepository
import com.alligator.reddittopposts.domain.model.Post
import javax.inject.Inject

/**
 * Provide next list of posts
 */

class GetNextPosts @Inject constructor(
    private val dataRepo: DataRepository
) {
    suspend operator fun invoke():List<Post>?{
        return dataRepo.getNextItems()
    }
}